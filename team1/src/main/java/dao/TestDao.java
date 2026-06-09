package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {
	private String baseSql = "SELECT * FROM student WHERE school_cd = ?　AND is_attend = true ";

	public Test get(Student student, Subject subject, School school, int no) {

		Test test = null;

		String sql = "SELECT * FROM test WHERE school_cd = ?"
				+ " AND student_no = ?"
				+ " AND subject_cd = ?"
				+ " AND no = ?";

		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, school.getCd());
			ps.setString(2, student.getNo());
			ps.setString(3, subject.getCd());
			ps.setInt(4, no);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				test = new Test();
				test.setNo(rs.getInt("no"));
				test.setPoint(rs.getInt("point"));
				test.setStudent(student);
				test.setSubject(subject);
				test.setSchool(school);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return test;
	}

	private List<Test> postFilter(ResultSet rs, School school) throws Exception {

		List<Test> list = new ArrayList<>();

		while (rs.next()) {

			Student student = new Student();
			student.setNo(rs.getString("no"));
			student.setName(rs.getString("name"));
			student.setEntYear(rs.getInt("ent_year"));
			student.setClassNum(rs.getString("class_num"));
			student.setSchool(school);

			Test test = new Test();
			test.setStudent(student);
			test.setSchool(school);

			list.add(test);
		}

		return list;
	}

	public List<Test> filter(int entYear, String classNum, Subject subject, int no, School school) {

		List<Test> list = new ArrayList<>();

		String sql = baseSql
				+ " AND ent_year = ?"
				+ " AND class_num = ?";

		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, school.getCd());
			ps.setInt(2, entYear);
			ps.setString(3, classNum);

			ResultSet rs = ps.executeQuery();
			list = postFilter(rs, school);

			for (Test test : list) {
				test.setSubject(subject);
				test.setNo(no);

				Test saved = get(test.getStudent(), subject, school, no);
				if (saved != null) {
					test.setPoint(saved.getPoint());
				} else {
					test.setPoint(-1);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public boolean save(List<Test> list) {

		try (Connection con = getConnection()) {
			con.setAutoCommit(false);

			for (Test test : list) {
				if (!save(test, con)) {
					con.rollback();
					return false;
				}
			}

			con.commit();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean save(Test test, Connection con) {

		String sql = "MERGE INTO test (student_no, subject_cd, no, point, school_cd, class_num) "
				+ "KEY(student_no, subject_cd, no, school_cd) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";

		try (PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, test.getStudent().getNo());
			ps.setString(2, test.getSubject().getCd());
			ps.setInt(3, test.getNo());
			ps.setInt(4, test.getPoint());
			ps.setString(5, test.getSchool().getCd());
			ps.setString(6, test.getStudent().getClassNum());

			ps.executeUpdate();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete(Test test) {

		String sql = "DELETE FROM test WHERE school_cd=? "
				+ "AND student_no=? "
				+ "AND subject_cd=? "
				+ "AND no=?";

		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, test.getSchool().getCd());
			ps.setString(2, test.getStudent().getNo());
			ps.setString(3, test.getSubject().getCd());
			ps.setInt(4, test.getNo());

			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
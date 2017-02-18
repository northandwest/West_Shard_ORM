package com.bucuoa.west.orm.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bucuoa.west.orm.core.converter.DBObjectConverter;
import com.bucuoa.west.orm.core.uitls.AnnoationUtil;
import com.bucuoa.west.orm.shard.holder.DbMasterOrSlaveHolder;

/**
 * sql执行器
 * 
 * @author jake
 *
 */
public class ExecuteManager<T, PK> {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public int doDML(String sql, Object[] objects) {
		DbMasterOrSlaveHolder.initMaster();

		Connection conn = null;
		PreparedStatement pst = null;

		int flag = 0;
		Session session = sessionFactory.getSession();

		try {

			conn = session.getConnection();

			pst = conn.prepareStatement(sql);

			if (objects != null) {
				for (int i = 0; i < objects.length; i++) {
					pst.setObject(i + 1, objects[i]);
				}
			}
			flag = pst.executeUpdate();
		} catch (Exception e) {
			logger.error("doDML error", e);
		} finally {
			sessionFactory.closeObject(pst);
			sessionFactory.closeObject(conn);
			sessionFactory.closeObject(session);
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	public PK executeUpdateSql(String sql) {
		DbMasterOrSlaveHolder.initMaster();

		Connection conn = null;
		PreparedStatement pst = null;

		Session session = sessionFactory.getSession();

		try {
			long start2 = System.currentTimeMillis();
			conn = session.getConnection();

			pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pst.executeUpdate();

			if (!conn.getAutoCommit()) {
				conn.commit();
			}
			long end2 = System.currentTimeMillis();

			logger.info("执行sql:{} use:{}ms,创建链接[{}]ms", sql, (end2 - start2), session.getCreateUseTime());
		} catch (Exception e) {
			logger.error("executeUpdateSql error", e);

		}

		PK id = null;

		ResultSet rs = null;
		try {
			rs = pst.getGeneratedKeys();
			if (rs.next()) {
				id = (PK) rs.getObject(1);
			}
		} catch (Exception e) {
			logger.error("executeUpdateSql id return is error", e);
		} finally {
			sessionFactory.closeObject(rs);
			sessionFactory.closeObject(pst);
			sessionFactory.closeObject(conn);
			sessionFactory.closeObject(session);
		}
		return id;
	}

	public boolean executeUpdateSqlNoID(String sql) {
		DbMasterOrSlaveHolder.initMaster();

		Connection conn = null;
		PreparedStatement pst = null;

		Session session = sessionFactory.getSession();
		boolean sussess = true;
		try {
			long start2 = System.currentTimeMillis();
			conn = session.getConnection();

			pst = conn.prepareStatement(sql);

			pst.executeUpdate();

			if (!conn.getAutoCommit()) {
				conn.commit();
			}
			long end2 = System.currentTimeMillis();

			logger.info("执行sql:{} use:{}ms,conection use:{}ms", sql, (end2 - start2), session.getCreateUseTime());

		} catch (Exception e) {
			logger.error("executeUpdateSqlNoID error", e);
			sussess = false;
		} finally {
			sessionFactory.closeObject(pst);
			sessionFactory.closeObject(conn);
			sessionFactory.closeObject(session);
		}
		return sussess;
	}


	public int queryCount(String sql, Object... objs) {
		DbMasterOrSlaveHolder.initSlave();

		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rst = null;

		int result = 0;

		Session session = sessionFactory.getSession();

		try {
			long start2 = System.currentTimeMillis();
			conn = session.getConnection();

			pst = conn.prepareStatement(sql);

			if (objs.length > 0) {
				for (int i = 0; i < objs.length; i++) {
					pst.setObject(i + 1, objs[i]);
				}
			}
			rst = pst.executeQuery();
			if (rst.next()) {

				result = rst.getInt(1);
			}
			long end2 = System.currentTimeMillis();
			logger.info("执行sql:{} use:{}ms,connection use{}ms", sql, (end2 - start2), session.getCreateUseTime());

		} catch (Exception e) {
			logger.error("queryCount error", e);
		} finally {
			sessionFactory.closeObject(rst);
			sessionFactory.closeObject(pst);
			sessionFactory.closeObject(conn);
			sessionFactory.closeObject(session);
		}

		return result;
	}


	public int queryCount(String sql) {
		DbMasterOrSlaveHolder.initSlave();

		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rst = null;

		int result = 0;

		Session session = sessionFactory.getSession();

		try {
			long start2 = System.currentTimeMillis();

			conn = session.getConnection();

			pst = conn.prepareStatement(sql);

			rst = pst.executeQuery();
			if (rst.next()) {

				result = rst.getInt(1);
			}
			long end2 = System.currentTimeMillis();

			logger.info("执行sql:{} use:{}ms,connection use:{}ms", sql, (end2 - start2), session.getCreateUseTime());

		} catch (Exception e) {
			logger.error("queryCount error", e);
		} finally {
			sessionFactory.closeObject(rst);
			sessionFactory.closeObject(pst);
			sessionFactory.closeObject(conn);
			sessionFactory.closeObject(session);
		}

		return result;
	}


	public <T> T queryOne(String sql, Class<T> t) {
		DbMasterOrSlaveHolder.initSlave();

		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rst = null;

		T obj = null;

		Session session = sessionFactory.getSession();

		try {
			long start2 = System.currentTimeMillis();

			conn = session.getConnection();

			pst = conn.prepareStatement(sql);
			rst = pst.executeQuery();

			obj = DBObjectConverter.getOneBean(t, rst);

			long end2 = System.currentTimeMillis();

			logger.info("执行sql:{} use:{}ms,connection use:{}ms", sql, (end2 - start2), session.getCreateUseTime());

		} catch (Exception e) {
			logger.error("queryOne error", e);

		} finally {
			sessionFactory.closeObject(rst);
			sessionFactory.closeObject(pst);
			sessionFactory.closeObject(conn);
			sessionFactory.closeObject(session);
		}
		return obj;
	}


	@SuppressWarnings("hiding")
	public <T> List<T> queryList(String sql, Class<T> clazz) {
		DbMasterOrSlaveHolder.initSlave();

		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rst = null;

		List<T> listBean;

		Session session = sessionFactory.getSession();
		try {
			long start2 = System.currentTimeMillis();

			conn = session.getConnection();

			pst = conn.prepareStatement(sql);

			rst = pst.executeQuery();

			listBean = DBObjectConverter.getListBean(clazz, rst);

			long end2 = System.currentTimeMillis();

			logger.info("执行sql:{} use:{}ms,connection use:{}ms", sql, (end2 - start2), session.getCreateUseTime());

		} catch (Exception e) {
			listBean = null;
			logger.error("queryList error", e);
		} finally {
			sessionFactory.closeObject(rst);
			sessionFactory.closeObject(pst);
			sessionFactory.closeObject(conn);
			sessionFactory.closeObject(session);
		}
		return listBean;
	}

	public <T> List<Map<String, Object>> queryListMap(String sql) {
		DbMasterOrSlaveHolder.initSlave();

		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rst = null;

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Session session = sessionFactory.getSession();
		try {
			long start2 = System.currentTimeMillis();

			conn = session.getConnection();

			pst = conn.prepareStatement(sql);
			rst = pst.executeQuery();

			List<String> columns = DBObjectConverter.getColumns(rst);

			list = DBObjectConverter.getListMap(rst, columns);

			long end2 = System.currentTimeMillis();

			logger.info("执行sql:{} use:{}ms,connection use:{}ms", sql, (end2 - start2), session.getCreateUseTime());

		} catch (Exception e) {
			logger.error("queryListMap error", e);
		} finally {
			sessionFactory.closeObject(rst);
			sessionFactory.closeObject(pst);
			sessionFactory.closeObject(conn);
			sessionFactory.closeObject(session);
		}
		return list;
	}


	public void addBatch(String sql, List<String[]> argsList) {
		DbMasterOrSlaveHolder.initMaster();

		Connection conn = null;
		PreparedStatement prest = null;

		Session session = sessionFactory.getSession();

		try {

			conn = session.getConnection();

			conn.setAutoCommit(false);

			prest = conn.prepareStatement(sql);

			for (String[] strings : argsList) {
				for (int i = 0; i < strings.length; i++) {
					prest.setString(i + 1, strings[i]);
				}
				prest.addBatch();
			}

			prest.executeBatch();
			if (!conn.getAutoCommit()) {
				conn.commit();
			}
		} catch (SQLException e) {
			logger.error("addBatch error", e);
		} finally {
			sessionFactory.closeObject(prest);
			sessionFactory.closeObject(conn);
			sessionFactory.closeObject(session);
		}
	}


	public void deleteById(Class<?> classk, Object id) {
		DbMasterOrSlaveHolder.initMaster();

		Connection conn = null;
		PreparedStatement prest = null;

		String tableName = AnnoationUtil.getTablename(classk);

		String sql = "delete from " + tableName + " where id=" + id;

		Session session = sessionFactory.getSession();
		try {
			long start2 = System.currentTimeMillis();

			conn = session.getConnection();

			conn.setAutoCommit(false);
			prest = conn.prepareStatement(sql);
			prest.executeUpdate();
			if (!conn.getAutoCommit()) {
				conn.commit();
			}

			long end2 = System.currentTimeMillis();

			logger.info("执行sql:{} use:{}ms,connection use:{}ms", sql, (end2 - start2), session.getCreateUseTime());

		} catch (SQLException e) {
			logger.error("deleteById error", e);

		} finally {
			sessionFactory.closeObject(prest);
			sessionFactory.closeObject(conn);
			sessionFactory.closeObject(session);
		}
	}


	public void deleteBy(String sql) {
		DbMasterOrSlaveHolder.initMaster();

		Connection conn = null;
		PreparedStatement prest = null;

		Session session = sessionFactory.getSession();
		try {

			long start2 = System.currentTimeMillis();

			conn = session.getConnection();

			conn.setAutoCommit(false);
			prest = conn.prepareStatement(sql);
			prest.executeUpdate();
			if (!conn.getAutoCommit()) {
				conn.commit();
			}

			long end2 = System.currentTimeMillis();

			logger.info("执行sql:{} use:{}ms,connection use:{}ms", sql, (end2 - start2), session.getCreateUseTime());
		} catch (SQLException e) {
			logger.error("deleteBy error", e);
		} finally {
			sessionFactory.closeObject(prest);
			sessionFactory.closeObject(conn);
			sessionFactory.closeObject(session);
		}
	}
}

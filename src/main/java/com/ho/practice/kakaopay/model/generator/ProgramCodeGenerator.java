package com.ho.practice.kakaopay.model.generator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class ProgramCodeGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		String prefix = "prg";
	    Connection connection = session.connection();

	    try {
	        Statement statement=connection.createStatement();

	        ResultSet rs=statement.executeQuery("select next_val as Id from kakao_seq_generator where sequence_name = 'program_code'");
	        statement.executeQuery("update kakao_seq_generator set next_val = next_val+1 where sequence_name = 'program_code'");
	        connection.commit();
	        
	        if(rs.next())
	        {
	            int id=rs.getInt(1);
	            String generatedId = prefix + String.format("%04d", id);
	            return generatedId;
	        }
	        
	    } catch (SQLException e) {
	    	try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	        e.printStackTrace();
	    }

	    return null;
	}

}

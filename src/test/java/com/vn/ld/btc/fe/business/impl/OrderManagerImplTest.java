package com.vn.ld.btc.fe.business.impl;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.vn.ld.api.order.service.RSOrderService;

@RunWith(EasyMockRunner.class)
public class OrderManagerImplTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPlaceOderToSystem() {
		RSOrderService transferService = EasyMock.createMock(RSOrderService.class);
	}

}

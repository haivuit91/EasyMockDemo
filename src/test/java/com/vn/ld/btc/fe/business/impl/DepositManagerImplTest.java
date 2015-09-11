package com.vn.ld.btc.fe.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.vn.ld.api.context.DepositSearchContext;
import com.vn.ld.api.dto.DepositsPageDTO;
import com.vn.ld.api.transfer.dto.CustomerAssetsDTO;
import com.vn.ld.api.transfer.dto.DepositDTO;
import com.vn.ld.api.transfer.service.RSTransferService;
import com.vn.ld.btc.fe.info.DepositInfo;

public class DepositManagerImplTest {
	@BeforeClass
	public static void setupOnce() {
		System.out.println("@BeforeClass runs only one time!");
	}

	@Before
	public void setup() {
		System.out.println("@Before runs before everytime a test method runs!");
	}

	@After
	public void tearDown() {
		System.out.println("@After runs after everytime a test method runs!");
	}

	@AfterClass
	public static void tearDownOnce() {
		System.out.println("@AfterClass runs only one time!");
	}

	@Test
	public void testCreateDeposit_NullInfo() {
		DepositManagerImpl depositManager = new DepositManagerImpl();
		Assert.assertFalse(depositManager.createDeposit(null));
	}

	@Test
	public void testCreateDeposit_Succeeded() {
		// class DepositManagerImpl contains a service to external system
		// (RSTransferService).
		// When do the unit test, we only care about our method => we will mock
		// the service!!!
		DepositManagerImpl depositManager = new DepositManagerImpl();
		DepositInfo depositInfo = new DepositInfo();
		depositInfo.setAmount("123");
		// create mock object
		RSTransferService transferService = EasyMock.createMock(RSTransferService.class);
		// expect the mock object returns "not blank" string (any time
		// "anyTimes()")
		EasyMock.expect(transferService.createNewDeposit(EasyMock.isA(DepositDTO.class))).andReturn("not blank")
				.anyTimes();
		// replay the mock object before use => must have
		EasyMock.replay(transferService);

		// because DepositManagerImpl does not have the setter for
		// "transferServiceClient"
		// we have use ReflectionTestUtil (spring-test) to set mock object to
		// DepositManagerImpl
		ReflectionTestUtils.setField(depositManager, "transferServiceClient", transferService);
		// assert the result
		Assert.assertTrue(depositManager.createDeposit(depositInfo));
		// verify the mock object after used => must have
		EasyMock.verify(transferService);
	}

	@Test
	public void testCreateDeposit_FailInService() {
		DepositManagerImpl depositManager = new DepositManagerImpl();
		DepositInfo depositInfo = new DepositInfo();
		depositInfo.setAmount("123");
		// create mock object
		RSTransferService transferService = EasyMock.createMock(RSTransferService.class);
		// expect the mock object returns blank string (one time "times(1)")
		// when set times(1), if the mock method is called 2 times => throw an
		// exception
		EasyMock.expect(transferService.createNewDeposit(EasyMock.isA(DepositDTO.class))).andReturn("").times(1);
		EasyMock.replay(transferService);
		ReflectionTestUtils.setField(depositManager, "transferServiceClient", transferService);
		// assert the result with assertThat
		Assert.assertThat(depositManager.createDeposit(depositInfo), CoreMatchers.is(CoreMatchers.equalTo(false)));
		EasyMock.verify(transferService);
	}

	@Test
	public void testGetCustomerAssets_ListNull() {
		DepositManagerImpl depositManager = new DepositManagerImpl();
		Assert.assertNull(depositManager.getCustomerAssets(null));
	}

	@Test
	public void testGetCustomerAssets_Succeeded() {
		DepositManagerImpl depositManager = new DepositManagerImpl();
		CustomerAssetsDTO ca = new CustomerAssetsDTO();
		ca.setCustomerAssetsId(3);
		List<CustomerAssetsDTO> assetsList = new ArrayList<CustomerAssetsDTO>();
		assetsList.add(ca);
		Integer customerId = 3;
		RSTransferService transferService = EasyMock.createMock(RSTransferService.class);
		EasyMock.expect(transferService.findCustomerAssets(customerId)).andReturn(assetsList).anyTimes();
		EasyMock.replay(transferService);
		ReflectionTestUtils.setField(depositManager, "transferServiceClient", transferService);
		Assert.assertEquals(1, depositManager.getCustomerAssets(customerId).size());
		EasyMock.verify(transferService);
	}

	@Test
	public void testGetCustomerAssets_FailInService() {
		DepositManagerImpl depositManager = new DepositManagerImpl();
		CustomerAssetsDTO ca = new CustomerAssetsDTO();
		ca.setCustomerAssetsId(3);
		List<CustomerAssetsDTO> assetsList = new ArrayList<CustomerAssetsDTO>();
		assetsList.add(ca);
		Integer customerId = 3;
		RSTransferService transferService = EasyMock.createMock(RSTransferService.class);
		EasyMock.expect(transferService.findCustomerAssets(customerId)).andReturn(null).times(1);
		EasyMock.replay(transferService);
		ReflectionTestUtils.setField(depositManager, "transferServiceClient", transferService);
		Assert.assertEquals(null, depositManager.getCustomerAssets(customerId));
		EasyMock.verify(transferService);
	}

	@Test
	public void testSearchDepositHistory_() {
		DepositManagerImpl depositManager = new DepositManagerImpl();
		DepositSearchContext dsc = new DepositSearchContext();
		DepositDTO dpd = new DepositDTO();
		dpd.setEmail("email");
		List<DepositDTO> depositDTOList = new ArrayList<DepositDTO>();
		depositDTOList.add(dpd);
		DepositsPageDTO appd = new DepositsPageDTO();
		appd.setDeposits(depositDTOList);
		RSTransferService transferService = EasyMock.createMock(RSTransferService.class);
		EasyMock.expect(transferService.searchDeposit(dsc, 1, 1)).andReturn(appd).anyTimes();
		EasyMock.replay(transferService);
		ReflectionTestUtils.setField(depositManager, "transferServiceClient", transferService);
		
		EasyMock.verify(transferService);
	}

}

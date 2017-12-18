package com.action;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.allinfinance.framework.constant.Const;
import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.constant.GetPropertytl;
import com.allinfinance.framework.dto.BaseDTO;
import com.allinfinance.framework.dto.OperationCtx;
import com.allinfinance.framework.dto.OperationRequest;
import com.allinfinance.framework.dto.OperationResult;
import com.allinfinance.framework.webservice.impl.WebServiceClientServiceImpl;
import com.allinfinance.framework.webservice.service.WebServiceClientService;
import com.allinfinance.shangqi.gateway.dto.CustomerUpdateForGatewayDTO;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/compress")
public class PicCompressController {
	
	@RequestMapping(value="/toIndex",method=RequestMethod.GET)
	public String toIndex(HttpServletRequest request,HttpServletResponse response, HttpSession session){
		return "index";
	}
	

	@RequestMapping(value="/pic",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public JSONObject picCompress(HttpServletRequest request,HttpServletResponse response, HttpSession session){
		
		String cardNo = request.getParameter("cardNo");
		String realName = request.getParameter("realName");
		String idNumber = request.getParameter("idNumber");
		String occupation = request.getParameter("occupation");
		String inputInvalidDate = request.getParameter("inputInvalidDate");
		String addressListFirstAdress = request.getParameter("addressListFirstAdress");
		String addressListSecondAdress = request.getParameter("addressListSecondAdress");
		String addressListThirdAdress = request.getParameter("addressListThirdAdress");
		String addressDetailAdress = request.getParameter("addressDetailAdress").trim();
		String cityAdressValue = request.getParameter("cityAdressValue").trim();
		String provinceAdressValue = request.getParameter("provinceAdressValue").trim();
		String distirctAdressValue = request.getParameter("distirctAdressValue").trim();
		String idPicFront = request.getParameter("idPicFront");
		String idPicBack = request.getParameter("idPicBack");
		String uId = request.getParameter("userId");
		
		CustomerUpdateForGatewayDTO customerUpdateForGatewayDTO = new CustomerUpdateForGatewayDTO();
		customerUpdateForGatewayDTO.setAwareness(occupation);
		customerUpdateForGatewayDTO.setCustomerAddress(provinceAdressValue + cityAdressValue + distirctAdressValue + addressDetailAdress);
		customerUpdateForGatewayDTO.setCustomerName(realName);
		customerUpdateForGatewayDTO.setCustomerTelephone("13764014947");
		customerUpdateForGatewayDTO.setEndValidity(inputInvalidDate);
		customerUpdateForGatewayDTO.setGender("2");
		customerUpdateForGatewayDTO.setIdNo(idNumber);
		customerUpdateForGatewayDTO.setIdType("1");
		customerUpdateForGatewayDTO.setNation("中国");
		customerUpdateForGatewayDTO.setNationality("汉族");
		customerUpdateForGatewayDTO.setCardNo(cardNo);
		System.out.println("开始请求 补录信息:"
				+ ConstCode.USERINFO_ADD + ",userId:" + uId);
		
		WebServiceClientService w = new WebServiceClientServiceImpl();
    	OperationResult result = sendService(
				ConstCode.USERINFO_ADD, customerUpdateForGatewayDTO,w);
		System.out.println("请求  补录信息 结束 ：返回result状态:" + result.getTxnstate());
		
		if("1".equals(result.getTxnstate())){
			boolean flag = true;
			
			if(flag){
				String fileServiceUrl="http://10.250.3.100:8989/ImageService/services/imageService?wsdl";
				String fileServiceNamespace="http://impl.entrance.trans.imageService.allinfinance.com";
				String fileServiceFunction="fileService";
				String subString="";
		        if (null!=idPicBack&&null!=idPicFront&& !StringUtils.isEmpty(idNumber)){
		            String frontImgStr = idPicFront;
		            String backImgStr = idPicBack;
		            String data = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DATA><TXN_CODE>I001</TXN_CODE><IMGF>"+frontImgStr+"</IMGF><IMGB>"+backImgStr+"</IMGB><IMGTYPE>1</IMGTYPE><IDNO>"+idNumber+"</IDNO></DATA>";
		            String respMessage = null;
		            try {
		                RPCServiceClient serviceClient = new RPCServiceClient();
		                Options options = serviceClient.getOptions();
		                EndpointReference targetEPR = new EndpointReference(fileServiceUrl);
		                options.setTo(targetEPR);
		                Object[] opAddEntryArgs = new Object[]{data};
		                Class[] classes = new Class[]{String.class};
		                QName opAddEntry = new QName(fileServiceNamespace,fileServiceFunction);
		                Object[] invokeBlocking = serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs, classes);
		                respMessage = (String) invokeBlocking[0];
		                subString = respMessage.substring(respMessage.indexOf("<RESPCODE>")+10,respMessage.indexOf("</RESPCODE>"));
		                //System.out.println("****"+subString);
		            } catch (AxisFault e) {
		                e.printStackTrace();
		            }
		            System.out.println("==============respMessage:"+subString+"=======================");
		        }
			}
		}
		JSONObject json = new JSONObject();
		json.put("success", true);
		json.put("responseCode", "00");
		return json;
	}
	
	public OperationResult sendService(String code, Object DTO,WebServiceClientService webServiceClientService) {

        OperationResult operationResult = null;
        try {
            OperationCtx operationCtx = new OperationCtx();
            //设置交易代码和交易用户
            operationCtx.setTxncode(code);
            operationCtx.setOprId("0000" + "");		//操作员id
            OperationRequest operationRequest = new OperationRequest();
            /**
             * 如果来的DTO为BaseDTO,则加上用户和发卡机构信息
             */
            if (DTO instanceof BaseDTO) {
                BaseDTO baseDTO = (BaseDTO) DTO;
                baseDTO.setLoginUserId("0000");
                baseDTO.setDefaultEntityId(GetPropertytl.getEntityId());
                operationRequest.setTxnvo(baseDTO);
            } else {
                operationRequest.setTxnvo(DTO);
            }
            // 调用后台请求
            operationResult = webServiceClientService.process(operationCtx, operationRequest);
            if (operationResult.getTxnstate().equals(Const.RETURN_FAILED)) {
                if (operationResult.getErrMessage() != null) {
                	System.err.println("errMessage:"+ operationResult.getErrMessage());
                }
                if (operationResult.getTxncause() != null) {
                    List causeList = (List) operationResult.getTxncause();
                    if (causeList != null) {
                        for (int i = 0; i < causeList.size(); i++) {
                        	System.err.println("cause:"+ (String) causeList.get(i));
                        }
                    }
                }
            }

        } catch (Exception e) {
        	e.printStackTrace();
        }
        return operationResult;
    }

}

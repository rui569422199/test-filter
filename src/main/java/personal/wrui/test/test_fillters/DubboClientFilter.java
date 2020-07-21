package personal.wrui.test.test_fillters;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSONObject;

import personal.wrui.test.test_fillters.utils.ThreadLocalUtils;

@Activate(group=Constants.CONSUMER)
public class DubboClientFilter extends BaseFilter implements Filter{

	private static final Log LOGGER = LogFactory.getLog(DubboClientFilter.class);
	
	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		String seq=ThreadLocalUtils.getSequenceId();
		LOGGER.debug("MethodName:"+invocation.getMethodName()+";get-head,seqId:" + seq);
		if(seq==null||seq.trim().isEmpty()) {
			seq=getNewSqId();
		}
		long start = System.currentTimeMillis();
		Result result=null;
		try {
			RpcContext.getContext().setAttachment(KEY_SEQID, seq);
			RpcContext.getContext().setAttachment(KEY_PROJECTANDMODULENAME, getProjectAndModuleName());
			Object[] oReqs = invocation.getArguments();
			LOGGER.debug("MethodName:"+invocation.getMethodName()+";seqId:" + RpcContext.getContext().getAttachment(KEY_SEQID)+";ProjectAndModuleName:" + RpcContext.getContext().getAttachment(KEY_PROJECTANDMODULENAME)+ ";reqObj:" +(oReqs==null?null: JSONObject.toJSONString(oReqs)));
			Object returnObj = null;
			result = invoker.invoke(invocation);
			if (result != null) {
				returnObj = result.getValue();
			}
			LOGGER.debug("MethodName:"+invocation.getMethodName()+";seqId:" + ThreadLocalUtils.getSequenceId() +";returnObj:" +(returnObj==null?null: JSONObject.toJSONString(returnObj)));
		} catch(Throwable e){
			LOGGER.error("MethodName:"+invocation.getMethodName()+";seqId:" + ThreadLocalUtils.getSequenceId() ,e);
		}finally {
			long end = System.currentTimeMillis();
			LOGGER.debug("MethodName:" + invocation.getMethodName() + ";seqId:" +ThreadLocalUtils.getSequenceId() +  ";time:" + (end - start));
		}
		return result;
	}

}

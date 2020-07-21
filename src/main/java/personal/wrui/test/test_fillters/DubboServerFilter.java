package personal.wrui.test.test_fillters;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSONObject;

import personal.wrui.test.test_fillters.utils.ThreadLocalUtils;

@Activate(group = Constants.PROVIDER, order = -10000)
public class DubboServerFilter extends BaseFilter implements Filter{

	private Log LOGGER = LogFactory.getLog(DubboServerFilter.class);
	
	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		String seq=invocation.getAttachment(KEY_SEQID);
		String projectandmodulename=invocation.getAttachment(KEY_PROJECTANDMODULENAME);
		LOGGER.debug("MethodName:"+invocation.getMethodName()+";get-head,seqId:" + seq + ";get-head,projectandmodulename:"+projectandmodulename);
		if(seq==null||seq.trim().isEmpty()) {
			seq=getNewSqId();
		}
		//RpcContext.getContext().set(KEY_SEQID, seq);
		ThreadLocalUtils.setSequenceId(seq);
		ThreadLocalUtils.setProjectAndModuleName(projectandmodulename);
		long start = System.currentTimeMillis();
		Result result=null;
		try {
			Object[] oReqs = invocation.getArguments();
			LOGGER.debug("MethodName:"+invocation.getMethodName()+";seqId:" +seq+ ";reqObj:" +(oReqs==null?null: JSONObject.toJSONString(oReqs)));
			Object returnObj = null;
			result = invoker.invoke(invocation);
			if (result != null) {
				returnObj = result.getValue();
			}
			LOGGER.debug("MethodName:"+invocation.getMethodName()+";seqId:" +seq+";returnObj:" +(returnObj==null?null: JSONObject.toJSONString(returnObj)));
		} catch(Throwable e){
			LOGGER.error("MethodName:"+invocation.getMethodName()+";seqId:" +seq,e);
		} finally {
			long end = System.currentTimeMillis();
			LOGGER.debug("MethodName:" + invocation.getMethodName() + ";seqId:" +seq+  ";time:" + (end - start));
		}
		return result;
	}

}

package personal.wrui.test.test_fillters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import personal.wrui.test.test_fillters.utils.ThreadLocalUtils;
@Component
public class SequenceIdFilter extends BaseFilter implements Filter{
	private Log logger = LogFactory.getLog(SequenceIdFilter.class);
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		long start = System.currentTimeMillis();
		HttpServletRequest req = (HttpServletRequest)request;
		if(req.getRequestURI().contains("/aehealth")) {
			chain.doFilter(request, response);
		}else {
			HttpServletResponse resp = (HttpServletResponse)response;
			String sequenceId;
			if(req.getHeader(KEY_SEQID)!=null){
				sequenceId = req.getHeader(KEY_SEQID);
			}else{
				sequenceId =  getNewSqId();
			}
			if(req.getHeader(KEY_PROJECTANDMODULENAME)!=null){
				ThreadLocalUtils.setProjectAndModuleName(req.getHeader(KEY_PROJECTANDMODULENAME));
			}else{
				ThreadLocalUtils.clearProjectAndModuleName();
			}
			try{
				ThreadLocalUtils.setSequenceId(sequenceId);
				logger.debug("req.getRequestURI():"+req.getRequestURI()+";sequenceId:"+ThreadLocalUtils.getSequenceId()+";ProjectAndModuleName:"+ThreadLocalUtils.getProjectAndModuleName());
				resp.addHeader(KEY_SEQID, sequenceId);
				resp.addHeader(KEY_PROJECTANDMODULENAME, ThreadLocalUtils.getProjectAndModuleName());
				chain.doFilter(request, response);
				long end = System.currentTimeMillis();
				logger.debug("req.getRequestURI():"+req.getRequestURI()+";sequenceId:"+ThreadLocalUtils.getSequenceId()+";ProjectAndModuleName:"+ThreadLocalUtils.getProjectAndModuleName()+";time:" + (end - start));
			}catch(Exception e){
				logger.error("req.getRequestURI():"+req.getRequestURI()+";sequenceId:"+ThreadLocalUtils.getSequenceId()+";ProjectAndModuleName:"+ThreadLocalUtils.getProjectAndModuleName()+"Exception:" + e, e);
			}finally{
				//清空本地线程中的数据
				personal.wrui.test.test_fillters.utils.ThreadLocalUtils.clear();
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}

package synapse.dementia.domain.admin.config.logs;

import java.io.IOException;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

public class CustomHttpServletResponseWrapper extends HttpServletResponseWrapper {
	private int httpStatus = SC_OK;
	private String errorMessage;

	public CustomHttpServletResponseWrapper(HttpServletResponse response) {
		super(response);
		if (response == null) {
			throw new IllegalArgumentException("Response cannot be null");
		}
	}

	@Override
	public void setStatus(int sc) {
		super.setStatus(sc);
		this.httpStatus = sc;
	}

	@Override
	public void sendError(int sc, String msg) throws IOException {
		super.sendError(sc, msg);
		this.httpStatus = sc;
		this.errorMessage = msg;
	}

	public int getStatus() {
		return this.httpStatus;
	}
}

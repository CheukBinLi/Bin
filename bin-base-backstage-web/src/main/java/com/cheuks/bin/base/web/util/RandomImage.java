package com.cheuks.bin.base.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RandomImage {

	String randomImageWriter(HttpServletResponse response, HttpServletRequest request);

}
package com.quisitive.currencyexchangeservice.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class SwaggerFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		request = new HttpServletRequestWrapper(request) {
		    private Set<String> headerNameSet;

		    @Override
		    public Enumeration<String> getHeaderNames() {
		        if (headerNameSet == null) {
		            // first time this method is called, cache the wrapped request's header names:
		            headerNameSet = new HashSet<>();
		            Enumeration<String> wrappedHeaderNames = super.getHeaderNames();
		            while (wrappedHeaderNames.hasMoreElements()) {
		                String headerName = wrappedHeaderNames.nextElement();
		                if (!"Forwarded".equalsIgnoreCase(headerName)) {
		                    headerNameSet.add(headerName);
		                }
		            }
		        }
		        return Collections.enumeration(headerNameSet);
		    }

		    @Override
		    public Enumeration<String> getHeaders(String name) {
		        if ("Forwarded".equalsIgnoreCase(name)) {
		            return Collections.<String>emptyEnumeration();
		        }
		        return super.getHeaders(name);
		    }

		    @Override
		    public String getHeader(String name) {
		        if ("Forwarded".equalsIgnoreCase(name)) {
		            return null;
		        }
		        return super.getHeader(name);
		    }
		};
	    filterChain.doFilter(request, response);
	}

}

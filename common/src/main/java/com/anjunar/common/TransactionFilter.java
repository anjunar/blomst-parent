package com.anjunar.common;

import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.transaction.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebFilter("/service/*")
public class TransactionFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(TransactionFilter.class);

    private final UserTransaction transaction;

    @Inject
    public TransactionFilter(UserTransaction transaction) {
        this.transaction = transaction;
    }

    public TransactionFilter() {
        this(null);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            transaction.begin();

            filterChain.doFilter(servletRequest, servletResponse);

            transaction.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException |
                 HeuristicRollbackException e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }
}

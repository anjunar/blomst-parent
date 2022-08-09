package com.anjunar.common.servlet;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class RequestResponseHolderListener implements ServletRequestListener
{

    private final boolean activated;

    public RequestResponseHolderListener()
    {
        this.activated = true;
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre)
    {
        if (activated)
        {
            /*
             * For some reason Tomcat seems to call requestInitialized() more than
             * once for a request. Not sure if this allowed according to the spec.
             */
            if (!RequestResponseHolder.REQUEST.isBound())
            {
                RequestResponseHolder.REQUEST.bind(sre.getServletRequest());
            }
        }
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre)
    {
        if (activated)
        {
            RequestResponseHolder.REQUEST.release();
        }
    }

}
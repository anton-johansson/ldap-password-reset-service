package com.antonjohansson.lprs.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Custom {@link SSLSocketFactory} implementation that trusts anything.
 */
public class TrustAnythingSSLSocketFactory extends SSLSocketFactory
{
    private SSLSocketFactory socketFactory;

    /**
     * Constructs a new {@link TrustAnythingSSLSocketFactory}.
     */
    public TrustAnythingSSLSocketFactory()
    {
        try
        {
            SSLContext context = SSLContext.getInstance("TLS");
            TrustManager trustManager = new DummyTrustManager();
            context.init(null, new TrustManager[] {trustManager}, new SecureRandom());
            socketFactory = context.getSocketFactory();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the default instance.
     *
     * @return Returns the default instance.
     */
    public static SSLSocketFactory getDefault()
    {
        return new TrustAnythingSSLSocketFactory();
    }

    @Override
    public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException
    {
        return socketFactory.createSocket(s, host, port, autoClose);
    }

    @Override
    public String[] getDefaultCipherSuites()
    {
        return socketFactory.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites()
    {
        return socketFactory.getSupportedCipherSuites();
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException, UnknownHostException
    {
        return socketFactory.createSocket(host, port);
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException
    {
        return socketFactory.createSocket(host, port);
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException
    {
        return socketFactory.createSocket(host, port, localHost, localPort);
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException
    {
        return socketFactory.createSocket(address, port, localAddress, localPort);
    }

    /**
     * An implementation of {@link TrustManager} that trusts anything.
     */
    public static class DummyTrustManager implements X509TrustManager
    {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException
        {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException
        {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers()
        {
            return new X509Certificate[0];
        }
    }
}

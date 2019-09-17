package com.platform.system.common.web.filter;

import java.io.ByteArrayInputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

/**
 * 可重复°的servlet输入流
 */
final class ResettableServletInputStream extends ServletInputStream {

    private final ByteArrayInputStream buffer;

    ResettableServletInputStream(byte[] contents) {
        this.buffer = new ByteArrayInputStream(contents);
    }

    @Override
    public int read() {
        return buffer.read();
    }

    @Override
    public boolean isFinished() {
        return buffer.available() == 0;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener arg0) {
        // ignored
    }

}

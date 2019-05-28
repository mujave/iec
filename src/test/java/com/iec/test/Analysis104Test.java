package com.iec.test;

import com.iec.analysis.exception.IllegalFormatException;
import com.iec.analysis.exception.LengthException;
import com.iec.analysis.exception.UnknownTransferReasonException;
import com.iec.analysis.exception.UnknownTypeIdentifierException;
import com.iec.analysis.protocol104.Analysis;
import org.junit.Test;

/**
 * @Author zhangyu
 * @create 2019/5/28 14:25
 */
public class Analysis104Test {

    @Test
    public void analysis() {

        try {
            String analysis = Analysis.analysis("68  0E  00  00  02  00  64  01  06  00  01  00  00  00  00  14".replaceAll(" ",""));
            System.out.println(analysis);
        } catch (IllegalFormatException e) {
            e.printStackTrace();
        } catch (LengthException e) {
            e.printStackTrace();
        } catch (UnknownTransferReasonException e) {
            e.printStackTrace();
        } catch (UnknownTypeIdentifierException e) {
            e.printStackTrace();
        }

    }

}

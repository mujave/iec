package com.iec.test;

import com.iec.analysis.exception.*;
import com.iec.analysis.protocol101.Analysis;
import org.junit.Test;

public class Analysis101Test {


    @Test
    public void fixed()  {

        String analysis = null;

        try {
            analysis = Analysis.analysis("104901004a16");
        } catch (LengthException e) {
            e.printStackTrace();
        } catch (UnknownTransferReasonException e) {
            e.printStackTrace();
        } catch (UnknownLinkCodeException e) {
            e.printStackTrace();
        } catch (CustomException e) {
            e.printStackTrace();
        } catch (UnknownTypeIdentifierException e) {
            e.printStackTrace();
        } catch (IllegalFormatException e) {
            e.printStackTrace();
        }
        System.out.println(analysis);
    }
    @Test
    public void elongate(){
        String analysis = null;
        try {
            analysis = Analysis.analysis("680b0b68d3010064010a01000000145816");
        } catch (LengthException e) {
            e.printStackTrace();
        } catch (CustomException e) {
            e.printStackTrace();
        } catch (UnknownLinkCodeException e) {
            e.printStackTrace();
        } catch (UnknownTransferReasonException e) {
            e.printStackTrace();
        } catch (UnknownTypeIdentifierException e) {
            e.printStackTrace();
        } catch (IllegalFormatException e) {
            e.printStackTrace();
        }
        System.out.println(analysis);
    }

}

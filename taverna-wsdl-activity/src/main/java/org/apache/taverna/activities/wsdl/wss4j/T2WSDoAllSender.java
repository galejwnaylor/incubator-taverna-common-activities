/*
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements. See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership. The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License. You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied. See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package org.apache.taverna.activities.wsdl.wss4j;

import java.util.Vector;

//import net.sf.taverna.t2.security.agents.SAException;
//import net.sf.taverna.t2.security.agents.WSSecurityAgent;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.apache.log4j.Logger;
//import org.apache.ws.axis.security.WSDoAllSender;
//import org.apache.ws.security.WSConstants;
//import org.apache.ws.security.WSEncryptionPart;
//import org.apache.ws.security.WSSConfig;
//import org.apache.ws.security.WSSecurityException;
//import org.apache.ws.security.handler.RequestData;
//import org.apache.ws.security.handler.WSHandlerConstants;
//import org.apache.ws.security.message.WSSecHeader;
//import org.apache.ws.security.util.WSSecurityUtil;
import org.w3c.dom.Document;

public class T2WSDoAllSender
{
    
}
//public class T2WSDoAllSender extends WSDoAllSender{
//	
//    /**
//	 * This is a security handler for wss4j that invokes security agents
//	 * whenever access to the user's credentials (passwords, private keys) 
//	 * or secret keys is required. The security agent performs the necessary
//	 * security operations on the message to be sent and returns the message. 
//	 */
//	private static final long serialVersionUID = 1L;
//
//	//private static Log log = LogFactory.getLog(T2WSDoAllSender.class.getName());
//
//   // private boolean doDebug = log.isDebugEnabled();
//    
//   // private static Logger logger = Logger.getLogger(T2WSDoAllSender.class);
//    
//    /**                                                             
//     * Performs all defined security actions to set-up the SOAP request.
//     * 
//     * This method overrides the doSenderAction() method of WSHandler class
//     * by setting the actions to be executed to use T2 security agents.
//     * 
//     * 
//     * @param doAction a set defining the actions to do 
//     * @param doc   the request as DOM document 
//     * @param reqData a data storage to pass values around bewteen methods
//     * @param actions a vector holding the actions to do in the order defined
//     *                in the deployment file or property
//     * @throws WSSecurityException
//     */
//    @Override
//    protected void doSenderAction(int doAction, Document doc,
//                                  RequestData reqData, Vector actions, 
//				  boolean isRequest)
//            throws WSSecurityException {
//    	
//
//        boolean mu = decodeMustUnderstand(reqData);
//
//        WSSConfig wssConfig = WSSConfig.getNewInstance();
//        
//        wssConfig
//	    .setEnableSignatureConfirmation(decodeEnableSignatureConfirmation(reqData));
//        
//        wssConfig
//	    .setPrecisionInMilliSeconds(decodeTimestampPrecision(reqData));
//        reqData.setWssConfig(wssConfig);
//
//        Object mc = reqData.getMsgContext();
//        String actor = getString(WSHandlerConstants.ACTOR, mc);
//        reqData.setActor(actor);
//
//        WSSecHeader secHeader = new WSSecHeader(actor, mu);
//        secHeader.insertSecurityHeader(doc);
//        
//        reqData.setSecHeader(secHeader);
//        reqData.setSoapConstants(WSSecurityUtil.getSOAPConstants(doc
//                .getDocumentElement()));
//        /*
//         * Here we have action, username, password, and actor, mustUnderstand.
//         * Now get the action specific parameters.
//         */
//        if ((doAction & WSConstants.UT) == WSConstants.UT) {
//            decodeUTParameter(reqData);
//        }
//        /*
//         * Here we have action, username, password, and actor, mustUnderstand.
//         * Now get the action specific parameters.
//         */
//        if ((doAction & WSConstants.UT_SIGN) == WSConstants.UT_SIGN) {
//            decodeUTParameter(reqData);
//            decodeSignatureParameter(reqData);
//        }
//        /*
//         * Get and check the Signature specific parameters first because they
//         * may be used for encryption too.
//         */
//        if ((doAction & WSConstants.SIGN) == WSConstants.SIGN) {
//            reqData.setSigCrypto(loadSignatureCrypto(reqData));
//            decodeSignatureParameter(reqData);
//        }
//        /*
//         * If we need to handle signed SAML token then we need may of the
//         * Signature parameters. The handle procedure loads the signature crypto
//         * file on demand, thus don't do it here.
//         */
//        if ((doAction & WSConstants.ST_SIGNED) == WSConstants.ST_SIGNED) {
//            decodeSignatureParameter(reqData);
//        }
//        /*
//         * Set and check the encryption specific parameters, if necessary take
//         * over signature parameters username and crypto instance.
//         */
//        if ((doAction & WSConstants.ENCR) == WSConstants.ENCR) {
//            reqData.setEncCrypto(loadEncryptionCrypto(reqData));
//            decodeEncryptionParameter(reqData);
//        }
//        /*
//         * If after all the parsing no Signature parts defined, set here a
//         * default set. This is necessary because we add SignatureConfirmation
//         * and therefore the default (Body) must be set here. The default setting
//         * in WSSignEnvelope doesn't work because the vector is not empty anymore.
//         */
//        if (reqData.getSignatureParts().isEmpty()) {
//            WSEncryptionPart encP = new WSEncryptionPart(reqData.getSoapConstants()
//                    .getBodyQName().getLocalPart(), reqData.getSoapConstants()
//                    .getEnvelopeURI(), "Content");
//            reqData.getSignatureParts().add(encP);
//        }
//        /*
//         * If SignatureConfirmation is enabled and this is a reqsponse then
//         * insert SignatureCOnfrmation elements, note their wsu:id in the signature
//         * parts. They will be signed automatically during a (probably) defined
//         * SIGN action.
//         */
//        if (wssConfig.isEnableSignatureConfirmation() && !isRequest) {
//            String done;
//            if ((done = (String) getProperty(reqData.getMsgContext(),
//                    WSHandlerConstants.SIG_CONF_DONE)) == null
//                    || !DONE.equals(done)) {
//                Vector results = null;
//                if ((results = (Vector) getProperty(reqData.getMsgContext(),
//                        WSHandlerConstants.RECV_RESULTS)) != null) {
//                    wssConfig.getAction(WSConstants.SC).execute(this, WSConstants.SC, doc, reqData);
//                }
//            }
//        }
//        /*
//         * Here we have all necessary information to perform the requested
//         * action(s).
//         */
//        
//        // Get the security agent
//       /* WSSecurityAgent sa = (WSSecurityAgent) ((MessageContext)reqData.getMsgContext()).getProperty("security_agent");
//   
//        // Perform security actions
//        for (int i = 0; i < actions.size(); i++) {
//
//            int actionToDo = ((Integer) actions.get(i)).intValue();
//            if (doDebug) {
//                log.debug("Performing Action: " + actionToDo);
//            }
//
//            switch (actionToDo) {
//                case WSConstants.UT:{
//                	try {
//    					sa.wssUsernameToken(doc, reqData);
//    				} catch (SAException e) {
//    					logger.error("", e);
//    				} break;
//				}
//                case WSConstants.ENCR:
//                case WSConstants.SIGN:{//sa.wssSign(doc, reqData); break;}
//                case WSConstants.ST_SIGNED:
//                case WSConstants.ST_UNSIGNED:
//                case WSConstants.TS:
//                case WSConstants.UT_SIGN:
//                    wssConfig.getAction(actionToDo).execute(this, actionToDo, doc, reqData);
//                    break;
//                case WSConstants.NO_SERIALIZE:
//                    reqData.setNoSerialization(true);
//                    break;
//            }
//        } */
//
//        /*
//         * If this is a request then store all signature values. Add ours to
//         * already gathered values because of chained handlers, e.g. for
//         * other actors.
//         */
//
//        if (wssConfig.isEnableSignatureConfirmation() && isRequest) {
//            if (reqData.getSignatureValues().size() > 0) {
//                Vector sigv = null;
//                if ((sigv = (Vector) getProperty(reqData.getMsgContext(),
//                        WSHandlerConstants.SEND_SIGV)) == null) {
//                    sigv = new Vector();
//                    setProperty(reqData.getMsgContext(),
//                            WSHandlerConstants.SEND_SIGV, sigv);
//                }
//                // sigv.add(reqData.getSignatureValues());
//                sigv.addAll(reqData.getSignatureValues());
//            }
//        }
//    }
//
//}



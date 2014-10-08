/**
 * Copyright (c) 2014 Mastek Ltd. All rights reserved.
 * 
 * This file is part of JBEAM. JBEAM is free software: you can
 * redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation.
 *
 * JBEAM is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for the specific language governing permissions and 
 * limitations.
 *
 * $Revision:  $
 *
 * $Header:   $
 *
 * $Log:    $
 *
 */
package com.myapp;

import java.net.InetAddress;
import java.net.NetworkInterface;

import org.bouncycastle.util.encoders.Base64;

/**
 * Add a one liner description of the class with a period at the end.
 *
 * Add multi-line description of the class indicating the objectives/purpose
 * of the class and the usage with each sentence ending with a period.
 *
 * @author Kedar Raybagkar
 * @since
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Throwable {
		InetAddress netaddr = InetAddress.getLocalHost();
		NetworkInterface intf = NetworkInterface.getByInetAddress(netaddr);
		byte[] address = intf.getHardwareAddress();
		if (intf.isVirtual()) {
			byte[] paddress = intf.getParent().getHardwareAddress();
			if (paddress != null) {
				address = paddress;
				intf = intf.getParent();
			}
		}
		if (address == null) {
			System.out.println("Unable to generate License Request..");
			return;
		}
		if (address.length == 0) {
			System.out.println("Unable to generate License Request..");
			return;
		}
		String str = new String(Base64.encode(address), "UTF-8");
		
		System.out.println("Machine Address \"" + str + "\". If this is shown as \"\" then we have a problem." );
	}

}

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
 *
 * $Revision: 2382 $
 *
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/utils/prefs/PREPreferences.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/stg/utils/prefs/PREPreferences.java $
 * 
 * 1     10/27/09 11:33a Kedarr
 * Initial Class
 * 
 *
 */
package stg.utils.prefs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.prefs.AbstractPreferences;
import java.util.prefs.BackingStoreException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import org.apache.log4j.Logger;

import com.stg.crypto.IOObfuscationUtility;
import com.stg.logger.LogLevel;

/**
 * Default implementation of the preferences.
 *
 * @author kedarr
 * @since V1.0R27 
 */
public class PREPreferences extends AbstractPreferences {

	private static final Logger logger = Logger.getLogger(PREPreferences.class);

	private Map<String,String> root;
	private Map<String,PREPreferences> children;
	private boolean isRemoved = false;

	/**
	 * Constructs the object of PREPreferences   
	 *
	 * @param parent Preferences
	 * @param name Name
	 */
	public PREPreferences(AbstractPreferences parent, String name) {
		super(parent, name);

		if (logger.isEnabledFor(LogLevel.FINEST)) {
			logger.log(LogLevel.FINEST, "Instantiating node " + name);
		}

		root = new TreeMap<String,String>();
		children = new TreeMap<String, PREPreferences>();

		try {
			sync();
		} catch (BackingStoreException e) {
				logger.fatal( "Unable to sync on creation of node " + name, e);
		}
	}

	/* (non-Javadoc)
	 * @see java.util.prefs.AbstractPreferences#putSpi(java.lang.String, java.lang.String)
	 */
	protected void putSpi(String key, String value) {
		root.put(key, value);
		try {
			flush();
		} catch (BackingStoreException e) {
			logger.fatal("Unable to flush after putting " + key, e);
		}
	}

	/* (non-Javadoc)
	 * @see java.util.prefs.AbstractPreferences#getSpi(java.lang.String)
	 */
	protected String getSpi(String key) {
		return (String) root.get(key);
	}

	/* (non-Javadoc)
	 * @see java.util.prefs.AbstractPreferences#removeSpi(java.lang.String)
	 */
	protected void removeSpi(String key) {
		root.remove(key);
		try {
			flush();
		} catch (BackingStoreException e) {
			logger.fatal("Unable to flush after removing " + key, e);
		}
	}

	/* (non-Javadoc)
	 * @see java.util.prefs.AbstractPreferences#removeNodeSpi()
	 */
	protected void removeNodeSpi() throws BackingStoreException {
		isRemoved = true;
		flush();
	}

	/* (non-Javadoc)
	 * @see java.util.prefs.AbstractPreferences#keysSpi()
	 */
	protected String[] keysSpi() throws BackingStoreException {
		return (String[]) root.keySet().toArray(
				new String[root.keySet().size()]);
	}

	/* (non-Javadoc)
	 * @see java.util.prefs.AbstractPreferences#childrenNamesSpi()
	 */
	protected String[] childrenNamesSpi() throws BackingStoreException {
		return (String[]) children.keySet().toArray(
				new String[children.keySet().size()]);
	}

	/* (non-Javadoc)
	 * @see java.util.prefs.AbstractPreferences#childSpi(java.lang.String)
	 */
	protected AbstractPreferences childSpi(String name) {
		PREPreferences child = (PREPreferences) children.get(name);
		if (child == null || child.isRemoved()) {
			child = new PREPreferences(this, name);
			children.put(name, child);
		}
		return child;
	}

	/* (non-Javadoc)
	 * @see java.util.prefs.AbstractPreferences#syncSpi()
	 */
	protected void syncSpi() throws BackingStoreException {
		if (isRemoved())
			return;

		final File file = PREPreferencesFactoryImpl.getPreferencesFile();
		if (file == null) {
			return;
		}

		if (!file.exists())
			return;

		synchronized (file) {
			IOObfuscationUtility obfuscator = new IOObfuscationUtility(); 
			Properties p = new Properties();
			try {
				InputStream is = null;
				try {
					is = obfuscator.getObfuscatedInputStream(new FileInputStream(file), Cipher.DECRYPT_MODE); 
					p.load(is);
					
				} finally {
					if (is != null) {
						is.close();
					}
				}

				StringBuilder sb = new StringBuilder();
				getPath(sb);
				String path = sb.toString();

				final Enumeration<?> pnen = p.propertyNames();
				while (pnen.hasMoreElements()) {
					String propKey = (String) pnen.nextElement();
					if (propKey.startsWith(path)) {
						String subKey = propKey.substring(path.length());
						// Only load immediate descendants
						if (subKey.indexOf('.') == -1) {
							root.put(subKey, p.getProperty(propKey));
						}
					}
				}
			} catch (IOException e) {
				throw new BackingStoreException(e);
			} catch (InvalidKeyException e) {
				logger.warn("Invalid Key", e);
			} catch (NoSuchAlgorithmException e) {
				logger.warn("No Such Algorithm", e);
			} catch (NoSuchProviderException e) {
				logger.warn("No Such Provider", e);
			} catch (NoSuchPaddingException e) {
				logger.warn("No Such Padding", e);
			} catch (InvalidAlgorithmParameterException e) {
				logger.warn("Invalid Algorithm", e);
			}
		}
	}

	/**
	 * Returns the path delimited with dots.
	 * 
	 * @param sb
	 */
	private void getPath(StringBuilder sb) {
		final PREPreferences parent = (PREPreferences) parent();
		if (parent == null)
			return;

		parent.getPath(sb);
		sb.append(name()).append('.');
	}

	/* (non-Javadoc)
	 * @see java.util.prefs.AbstractPreferences#flushSpi()
	 */
	protected void flushSpi() throws BackingStoreException {
		final File file = PREPreferencesFactoryImpl.getPreferencesFile();
		if (file == null) {
			return;
		}
		synchronized (file) {
			IOObfuscationUtility obfuscator = new IOObfuscationUtility();
			Properties p = new Properties();
			try {

				StringBuilder sb = new StringBuilder();
				getPath(sb);
				String path = sb.toString();

				if (file.exists()) {
					InputStream is = null;
					try {
						is = obfuscator.getObfuscatedInputStream(new FileInputStream(file), Cipher.DECRYPT_MODE);
						p.load(is);
					} finally {
						if (is != null) {
							is.close();
						}
					}
					

					List<String> toRemove = new ArrayList<String>();

					// Make a list of all direct children of this node to be
					// removed
					final Enumeration<?> pnen = p.propertyNames();
					while (pnen.hasMoreElements()) {
						String propKey = (String) pnen.nextElement();
						if (propKey.startsWith(path)) {
							String subKey = propKey.substring(path.length());
							// Only do immediate descendants
							if (subKey.indexOf('.') == -1) {
								toRemove.add(propKey);
							}
						}
					}

					// Remove them now that the enumeration is done with
					for (Iterator<?> iterator = toRemove.iterator(); iterator
							.hasNext();) {
						iterator.next();
						iterator.remove();
					}
				}

				// If this node hasn't been removed, add back in any values
				if (!isRemoved) {
					for (Iterator<Entry<String,String>> iterator = root.entrySet().iterator(); iterator
							.hasNext();) {
						Entry<String, String> entry = iterator.next();
						p.setProperty(path + entry.getKey(), entry.getValue());
					}
				}
				OutputStream os = null;
				try {
					os = obfuscator.getObfuscatedOutputStream(new FileOutputStream(file), Cipher.ENCRYPT_MODE);
					p.store(os, "PREPrefences");
				} finally {
					if (os != null) {
						os.close();
					}
				}
			} catch (IOException e) {
				throw new BackingStoreException(e);
			} catch (InvalidKeyException e) {
				logger.error("Invalid Key", e);
			} catch (NoSuchAlgorithmException e) {
				logger.error("No Such Algorithm", e);
			} catch (NoSuchProviderException e) {
				logger.error("No Such Provider", e);
			} catch (NoSuchPaddingException e) {
				logger.error("No Such Padding", e);
			} catch (InvalidAlgorithmParameterException e) {
				logger.error("Invalid Algorithm Parameter", e);
			}
		}
	}
}

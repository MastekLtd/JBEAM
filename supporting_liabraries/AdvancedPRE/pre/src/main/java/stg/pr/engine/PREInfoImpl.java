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
 * $Revision: $
 * 
 * $Header: $
 * 
 * $Log: $
 */
package stg.pr.engine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import stg.utils.ResourceUtils;
import stg.utils.StringUtils;

/**
 * Implementation of the {@link PREInfo}
 * 
 * @author kedar460043
 * @since
 */
public class PREInfoImpl implements PREInfo {

    /**
	 * 
	 */
    private static final long serialVersionUID = 5319611220164575998L;

    private String name = "?";

    private String version = "?";

    private long bundledOn = Long.MIN_VALUE;

    private int majorVersion = Integer.MIN_VALUE;

    private int minorVersion = Integer.MIN_VALUE;

    private int macroVersion = Integer.MIN_VALUE;

    private boolean snapshot = true;

    private String symbolicName = "?";

    private int buildNumber = Integer.MIN_VALUE;

    private int patchVersion;

    PREInfoImpl() {
        readManifest();
    }

    private void readManifest() {
        Manifest mf = loadManifest();
        Attributes attributes = mf.getMainAttributes();
        name = attributes.getValue("Bundle-Name");
        version = attributes.getValue("Bundle-Version");
        buildNumber = Integer.parseInt(attributes.getValue("Build-Number"));
        try {
            bundledOn = Long.parseLong(attributes.getValue("Bundled-On"));
        } catch (NumberFormatException e) {
            // do nothing
        }
        String[] versionStrings = StringUtils.extractAllTokens(version, '.', '.', '/', false);
        if (versionStrings.length == 4) {
            majorVersion = Integer.parseInt(versionStrings[0]);
            minorVersion = Integer.parseInt(versionStrings[1]);
            minorVersion = Integer.parseInt(versionStrings[2]);
            String tmp = versionStrings[3];
            if (tmp.endsWith("-SNAPSHOT")) {
                snapshot = true;
                tmp = tmp.replace("-SNAPSHOT", "");
            }
            patchVersion = Integer.parseInt(tmp);
        }
    }

    private Manifest loadManifest() {
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().putValue("Bundle-Name", "Process Request Engine");
        manifest.getMainAttributes().putValue("Bundle-Version", "0.0.0.0-SNAPSHOT");
        manifest.getMainAttributes().putValue("Build-Number", "0");
        manifest.getMainAttributes().putValue("Bundled-On", System.currentTimeMillis()+"");
        try {
            InputStream is = ResourceUtils.getResourceAsStream(getClass(), "MANIFEST.MF");
            manifest = new Manifest(is);
        } catch (IOException e) {
            String localFile = getClass().getProtectionDomain().getCodeSource().getLocation().toString();
            localFile = localFile.concat("!/");
            String tmpString = "jar:";
            String localJarFileString = tmpString.concat(localFile);
            URL localJarFileURL;
            try {
                localJarFileURL = new URL(localJarFileString);
                JarURLConnection localJarFile = (JarURLConnection) localJarFileURL.openConnection();
                manifest = localJarFile.getManifest();
            } catch (MalformedURLException murle) {
                // do nothing
            } catch (FileNotFoundException fnfe) {
                // do nothing
            } catch (IOException ioe) {
                // do nothing
            }
        }
        return manifest;
    }

    /*
     * (non-Javadoc)
     * 
     * @see stg.pr.engine.PREInfo#getName()
     */
    public String getName() {
        return name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see stg.pr.engine.PREInfo#getVersion()
     */
    public String getVersion() {
        return version;
    }

    /**
     * Is used by the engine to store the version number
     * 
     * @param version
     */
    protected void setVersion(String version) {
        this.version = version;
    }

    /*
     * (non-Javadoc)
     * 
     * @see stg.pr.engine.PREInfo#getBundledOn()
     */
    public long getBundledOn() {
        return bundledOn;
    }

    /*
     * (non-Javadoc)
     * 
     * @see stg.pr.engine.PREInfo#getMajorVersion()
     */
    public int getMajorVersion() {
        return majorVersion;
    }

    /*
     * (non-Javadoc)
     * 
     * @see stg.pr.engine.PREInfo#getMinorVersion()
     */
    public int getMinorVersion() {
        return minorVersion;
    }

    /*
     * (non-Javadoc)
     * 
     * @see stg.pr.engine.PREInfo#getMacroVersion()
     */
    public int getMacroVersion() {
        return macroVersion;
    }

    /*
     * (non-Javadoc)
     * 
     * @see stg.pr.engine.PREInfo#isSnapshot()
     */
    public boolean isSnapshot() {
        return snapshot;
    }

    /*
     * (non-Javadoc)
     * 
     * @see stg.pr.engine.PREInfo#getSymbolicName()
     */
    public String getSymbolicName() {
        return symbolicName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Product Name :");
        builder.append(name);
        builder.append(" Product Version :");
        builder.append(version);
        builder.append(" Bundled On :");
        builder.append(bundledOn);
        builder.append(" Build Number :");
        builder.append(buildNumber);
        return builder.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see stg.pr.engine.PREInfo#getBuildNumber()
     */
    public int getBuildNumber() {
        return buildNumber;
    }

    @Override
    public int getPatchVersion() {
        return patchVersion;
    }
}

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
 * $Revision: 2 $
 * 
 * $Header: /Utilities/JDBCPool/src/jdbc/tuning/DatabaseMetaDataWrapper.java 2     3/17/08 12:28p Kedarr $
 * 
 * $Log: /Utilities/JDBCPool/src/jdbc/tuning/DatabaseMetaDataWrapper.java $
 * 
 * 2     3/17/08 12:28p Kedarr
 * 
 * 1     5/09/05 2:37p Kedarr
 * Initial Version
 * 
 * 1 12/01/03 1:35p Kedarr Revision 1.1 2003/11/28 09:47:21 kedar Added a new
 * package for Tunning SQL queries.
 * 
 *  
 */
package jdbc.tuning;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.RowIdLifetime;
import java.sql.SQLException;

public class DatabaseMetaDataWrapper implements DatabaseMetaData {

    public final String REVISION = "$Revision: 2 $";

    protected DatabaseMetaData realMetadata;

    protected ConnectionWrapper connectionParent;

    public DatabaseMetaDataWrapper(DatabaseMetaData metadata,
            ConnectionWrapper parent) {
        realMetadata = metadata;
        connectionParent = parent;
    }

    public boolean allProceduresAreCallable() throws SQLException {
        return realMetadata.allProceduresAreCallable();
    }

    public boolean allTablesAreSelectable() throws SQLException {
        return realMetadata.allTablesAreSelectable();
    }

    public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
        return realMetadata.dataDefinitionCausesTransactionCommit();
    }

    public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
        return realMetadata.dataDefinitionIgnoredInTransactions();
    }

    public boolean deletesAreDetected(int type) throws SQLException {
        return realMetadata.deletesAreDetected(type);
    }

    public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
        return realMetadata.doesMaxRowSizeIncludeBlobs();
    }

    public ResultSet getBestRowIdentifier(String catalog, String schema,
            String table, int scope, boolean nullable) throws SQLException {
        return realMetadata.getBestRowIdentifier(catalog, schema, table, scope,
                nullable);
    }

    public ResultSet getCatalogs() throws SQLException {
        return realMetadata.getCatalogs();
    }

    public String getCatalogSeparator() throws SQLException {
        return realMetadata.getCatalogSeparator();
    }

    public String getCatalogTerm() throws SQLException {
        return realMetadata.getCatalogTerm();
    }

    public ResultSet getColumnPrivileges(String catalog, String schema,
            String table, String columnNamePattern) throws SQLException {
        return realMetadata.getColumnPrivileges(catalog, schema, table,
                columnNamePattern);
    }

    public ResultSet getColumns(String catalog, String schemaPattern,
            String tableNamePattern, String columnNamePattern)
            throws SQLException {
        return realMetadata.getColumns(catalog, schemaPattern,
                tableNamePattern, columnNamePattern);
    }

    public Connection getConnection() throws SQLException {
        return connectionParent;
    }

    public ResultSet getCrossReference(String primaryCatalog,
            String primarySchema, String primaryTable, String foreignCatalog,
            String foreignSchema, String foreignTable) throws SQLException {
        return realMetadata.getCrossReference(primaryCatalog, primarySchema,
                primaryTable, foreignCatalog, foreignSchema, foreignTable);
    }

    public String getDatabaseProductName() throws SQLException {
        return realMetadata.getDatabaseProductName();
    }

    public String getDatabaseProductVersion() throws SQLException {
        return realMetadata.getDatabaseProductVersion();
    }

    public int getDefaultTransactionIsolation() throws SQLException {
        return realMetadata.getDefaultTransactionIsolation();
    }

    public int getDriverMajorVersion() {
        return realMetadata.getDriverMajorVersion();
    }

    public int getDriverMinorVersion() {
        return realMetadata.getDriverMinorVersion();
    }

    public String getDriverName() throws SQLException {
        return realMetadata.getDriverName();
    }

    public String getDriverVersion() throws SQLException {
        return realMetadata.getDriverVersion();
    }

    public ResultSet getExportedKeys(String catalog, String schema, String table)
            throws SQLException {
        return realMetadata.getExportedKeys(catalog, schema, table);
    }

    public String getExtraNameCharacters() throws SQLException {
        return realMetadata.getExtraNameCharacters();
    }

    public String getIdentifierQuoteString() throws SQLException {
        return realMetadata.getIdentifierQuoteString();
    }

    public ResultSet getImportedKeys(String catalog, String schema, String table)
            throws SQLException {
        return realMetadata.getImportedKeys(catalog, schema, table);
    }

    public ResultSet getIndexInfo(String catalog, String schema, String table,
            boolean unique, boolean approximate) throws SQLException {
        return realMetadata.getIndexInfo(catalog, schema, table, unique,
                approximate);
    }

    public int getMaxBinaryLiteralLength() throws SQLException {
        return realMetadata.getMaxBinaryLiteralLength();
    }

    public int getMaxCatalogNameLength() throws SQLException {
        return realMetadata.getMaxCatalogNameLength();
    }

    public int getMaxCharLiteralLength() throws SQLException {
        return realMetadata.getMaxCharLiteralLength();
    }

    public int getMaxColumnNameLength() throws SQLException {
        return realMetadata.getMaxColumnNameLength();
    }

    public int getMaxColumnsInGroupBy() throws SQLException {
        return realMetadata.getMaxColumnsInGroupBy();
    }

    public int getMaxColumnsInIndex() throws SQLException {
        return realMetadata.getMaxColumnsInIndex();
    }

    public int getMaxColumnsInOrderBy() throws SQLException {
        return realMetadata.getMaxColumnsInOrderBy();
    }

    public int getMaxColumnsInSelect() throws SQLException {
        return realMetadata.getMaxColumnsInSelect();
    }

    public int getMaxColumnsInTable() throws SQLException {
        return realMetadata.getMaxColumnsInTable();
    }

    public int getMaxConnections() throws SQLException {
        return realMetadata.getMaxConnections();
    }

    public int getMaxCursorNameLength() throws SQLException {
        return realMetadata.getMaxCursorNameLength();
    }

    public int getMaxIndexLength() throws SQLException {
        return realMetadata.getMaxIndexLength();
    }

    public int getMaxProcedureNameLength() throws SQLException {
        return realMetadata.getMaxProcedureNameLength();
    }

    public int getMaxRowSize() throws SQLException {
        return realMetadata.getMaxRowSize();
    }

    public int getMaxSchemaNameLength() throws SQLException {
        return realMetadata.getMaxSchemaNameLength();
    }

    public int getMaxStatementLength() throws SQLException {
        return realMetadata.getMaxStatementLength();
    }

    public int getMaxStatements() throws SQLException {
        return realMetadata.getMaxStatements();
    }

    public int getMaxTableNameLength() throws SQLException {
        return realMetadata.getMaxTableNameLength();
    }

    public int getMaxTablesInSelect() throws SQLException {
        return realMetadata.getMaxTablesInSelect();
    }

    public int getMaxUserNameLength() throws SQLException {
        return realMetadata.getMaxUserNameLength();
    }

    public String getNumericFunctions() throws SQLException {
        return realMetadata.getNumericFunctions();
    }

    public ResultSet getPrimaryKeys(String catalog, String schema, String table)
            throws SQLException {
        return realMetadata.getPrimaryKeys(catalog, schema, table);
    }

    public ResultSet getProcedureColumns(String catalog, String schemaPattern,
            String procedureNamePattern, String columnNamePattern)
            throws SQLException {
        return realMetadata.getProcedureColumns(catalog, schemaPattern,
                procedureNamePattern, columnNamePattern);
    }

    public ResultSet getProcedures(String catalog, String schemaPattern,
            String procedureNamePattern) throws SQLException {
        return realMetadata.getProcedures(catalog, schemaPattern,
                procedureNamePattern);
    }

    public String getProcedureTerm() throws SQLException {
        return realMetadata.getProcedureTerm();
    }

    public ResultSet getSchemas() throws SQLException {
        return realMetadata.getSchemas();
    }

    public String getSchemaTerm() throws SQLException {
        return realMetadata.getSchemaTerm();
    }

    public String getSearchStringEscape() throws SQLException {
        return realMetadata.getSearchStringEscape();
    }

    public String getSQLKeywords() throws SQLException {
        return realMetadata.getSQLKeywords();
    }

    public String getStringFunctions() throws SQLException {
        return realMetadata.getStringFunctions();
    }

    public String getSystemFunctions() throws SQLException {
        return realMetadata.getSystemFunctions();
    }

    public ResultSet getTablePrivileges(String catalog, String schemaPattern,
            String tableNamePattern) throws SQLException {
        return realMetadata.getTablePrivileges(catalog, schemaPattern,
                tableNamePattern);
    }

    public ResultSet getTables(String catalog, String schemaPattern,
            String tableNamePattern, String[] types) throws SQLException {
        return realMetadata.getTables(catalog, schemaPattern, tableNamePattern,
                types);
    }

    public ResultSet getTableTypes() throws SQLException {
        return realMetadata.getTableTypes();
    }

    public String getTimeDateFunctions() throws SQLException {
        return realMetadata.getTimeDateFunctions();
    }

    public ResultSet getTypeInfo() throws SQLException {
        return realMetadata.getTypeInfo();
    }

    public ResultSet getUDTs(String catalog, String schemaPattern,
            String typeNamePattern, int[] types) throws SQLException {
        return realMetadata.getUDTs(catalog, schemaPattern, typeNamePattern,
                types);
    }

    public String getURL() throws SQLException {
        return realMetadata.getURL();
    }

    public String getUserName() throws SQLException {
        return realMetadata.getUserName();
    }

    public ResultSet getVersionColumns(String catalog, String schema,
            String table) throws SQLException {
        return realMetadata.getVersionColumns(catalog, schema, table);
    }

    public boolean insertsAreDetected(int type) throws SQLException {
        return realMetadata.insertsAreDetected(type);
    }

    public boolean isCatalogAtStart() throws SQLException {
        return realMetadata.isCatalogAtStart();
    }

    public boolean isReadOnly() throws SQLException {
        return realMetadata.isReadOnly();
    }

    public boolean nullPlusNonNullIsNull() throws SQLException {
        return realMetadata.nullPlusNonNullIsNull();
    }

    public boolean nullsAreSortedAtEnd() throws SQLException {
        return realMetadata.nullsAreSortedAtEnd();
    }

    public boolean nullsAreSortedAtStart() throws SQLException {
        return realMetadata.nullsAreSortedAtStart();
    }

    public boolean nullsAreSortedHigh() throws SQLException {
        return realMetadata.nullsAreSortedHigh();
    }

    public boolean nullsAreSortedLow() throws SQLException {
        return realMetadata.nullsAreSortedLow();
    }

    public boolean othersDeletesAreVisible(int type) throws SQLException {
        return realMetadata.othersDeletesAreVisible(type);
    }

    public boolean othersInsertsAreVisible(int type) throws SQLException {
        return realMetadata.othersInsertsAreVisible(type);
    }

    public boolean othersUpdatesAreVisible(int type) throws SQLException {
        return realMetadata.othersUpdatesAreVisible(type);
    }

    public boolean ownDeletesAreVisible(int type) throws SQLException {
        return realMetadata.ownDeletesAreVisible(type);
    }

    public boolean ownInsertsAreVisible(int type) throws SQLException {
        return realMetadata.ownInsertsAreVisible(type);
    }

    public boolean ownUpdatesAreVisible(int type) throws SQLException {
        return realMetadata.ownUpdatesAreVisible(type);
    }

    public boolean storesLowerCaseIdentifiers() throws SQLException {
        return realMetadata.storesLowerCaseIdentifiers();
    }

    public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
        return realMetadata.storesLowerCaseQuotedIdentifiers();
    }

    public boolean storesMixedCaseIdentifiers() throws SQLException {
        return realMetadata.storesMixedCaseIdentifiers();
    }

    public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
        return realMetadata.storesMixedCaseQuotedIdentifiers();
    }

    public boolean storesUpperCaseIdentifiers() throws SQLException {
        return realMetadata.storesUpperCaseIdentifiers();
    }

    public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
        return realMetadata.storesUpperCaseQuotedIdentifiers();
    }

    public boolean supportsAlterTableWithAddColumn() throws SQLException {
        return realMetadata.supportsAlterTableWithAddColumn();
    }

    public boolean supportsAlterTableWithDropColumn() throws SQLException {
        return realMetadata.supportsAlterTableWithDropColumn();
    }

    public boolean supportsANSI92EntryLevelSQL() throws SQLException {
        return realMetadata.supportsANSI92EntryLevelSQL();
    }

    public boolean supportsANSI92FullSQL() throws SQLException {
        return realMetadata.supportsANSI92FullSQL();
    }

    public boolean supportsANSI92IntermediateSQL() throws SQLException {
        return realMetadata.supportsANSI92IntermediateSQL();
    }

    public boolean supportsBatchUpdates() throws SQLException {
        return realMetadata.supportsBatchUpdates();
    }

    public boolean supportsCatalogsInDataManipulation() throws SQLException {
        return realMetadata.supportsCatalogsInDataManipulation();
    }

    public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
        return realMetadata.supportsCatalogsInIndexDefinitions();
    }

    public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
        return realMetadata.supportsCatalogsInPrivilegeDefinitions();
    }

    public boolean supportsCatalogsInProcedureCalls() throws SQLException {
        return realMetadata.supportsCatalogsInProcedureCalls();
    }

    public boolean supportsCatalogsInTableDefinitions() throws SQLException {
        return realMetadata.supportsCatalogsInTableDefinitions();
    }

    public boolean supportsColumnAliasing() throws SQLException {
        return realMetadata.supportsColumnAliasing();
    }

    public boolean supportsConvert() throws SQLException {
        return realMetadata.supportsConvert();
    }

    public boolean supportsConvert(int fromType, int toType)
            throws SQLException {
        return realMetadata.supportsConvert(fromType, toType);
    }

    public boolean supportsCoreSQLGrammar() throws SQLException {
        return realMetadata.supportsCoreSQLGrammar();
    }

    public boolean supportsCorrelatedSubqueries() throws SQLException {
        return realMetadata.supportsCorrelatedSubqueries();
    }

    public boolean supportsDataDefinitionAndDataManipulationTransactions()
            throws SQLException {
        return realMetadata
                .supportsDataDefinitionAndDataManipulationTransactions();
    }

    public boolean supportsDataManipulationTransactionsOnly()
            throws SQLException {
        return realMetadata.supportsDataManipulationTransactionsOnly();
    }

    public boolean supportsDifferentTableCorrelationNames() throws SQLException {
        return realMetadata.supportsDifferentTableCorrelationNames();
    }

    public boolean supportsExpressionsInOrderBy() throws SQLException {
        return realMetadata.supportsExpressionsInOrderBy();
    }

    public boolean supportsExtendedSQLGrammar() throws SQLException {
        return realMetadata.supportsExtendedSQLGrammar();
    }

    public boolean supportsFullOuterJoins() throws SQLException {
        return realMetadata.supportsFullOuterJoins();
    }

    public boolean supportsGroupBy() throws SQLException {
        return realMetadata.supportsGroupBy();
    }

    public boolean supportsGroupByBeyondSelect() throws SQLException {
        return realMetadata.supportsGroupByBeyondSelect();
    }

    public boolean supportsGroupByUnrelated() throws SQLException {
        return realMetadata.supportsGroupByUnrelated();
    }

    public boolean supportsIntegrityEnhancementFacility() throws SQLException {
        return realMetadata.supportsIntegrityEnhancementFacility();
    }

    public boolean supportsLikeEscapeClause() throws SQLException {
        return realMetadata.supportsLikeEscapeClause();
    }

    public boolean supportsLimitedOuterJoins() throws SQLException {
        return realMetadata.supportsLimitedOuterJoins();
    }

    public boolean supportsMinimumSQLGrammar() throws SQLException {
        return realMetadata.supportsMinimumSQLGrammar();
    }

    public boolean supportsMixedCaseIdentifiers() throws SQLException {
        return realMetadata.supportsMixedCaseIdentifiers();
    }

    public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
        return realMetadata.supportsMixedCaseQuotedIdentifiers();
    }

    public boolean supportsMultipleResultSets() throws SQLException {
        return realMetadata.supportsMultipleResultSets();
    }

    public boolean supportsMultipleTransactions() throws SQLException {
        return realMetadata.supportsMultipleTransactions();
    }

    public boolean supportsNonNullableColumns() throws SQLException {
        return realMetadata.supportsNonNullableColumns();
    }

    public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
        return realMetadata.supportsOpenCursorsAcrossCommit();
    }

    public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
        return realMetadata.supportsOpenCursorsAcrossRollback();
    }

    public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
        return realMetadata.supportsOpenStatementsAcrossCommit();
    }

    public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
        return realMetadata.supportsOpenStatementsAcrossRollback();
    }

    public boolean supportsOrderByUnrelated() throws SQLException {
        return realMetadata.supportsOrderByUnrelated();
    }

    public boolean supportsOuterJoins() throws SQLException {
        return realMetadata.supportsOuterJoins();
    }

    public boolean supportsPositionedDelete() throws SQLException {
        return realMetadata.supportsPositionedDelete();
    }

    public boolean supportsPositionedUpdate() throws SQLException {
        return realMetadata.supportsPositionedUpdate();
    }

    public boolean supportsResultSetConcurrency(int type, int concurrency)
            throws SQLException {
        return realMetadata.supportsResultSetConcurrency(type, concurrency);
    }

    public boolean supportsResultSetType(int type) throws SQLException {
        return realMetadata.supportsResultSetType(type);
    }

    public boolean supportsSchemasInDataManipulation() throws SQLException {
        return realMetadata.supportsSchemasInDataManipulation();
    }

    public boolean supportsSchemasInIndexDefinitions() throws SQLException {
        return realMetadata.supportsSchemasInIndexDefinitions();
    }

    public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
        return realMetadata.supportsSchemasInPrivilegeDefinitions();
    }

    public boolean supportsSchemasInProcedureCalls() throws SQLException {
        return realMetadata.supportsSchemasInProcedureCalls();
    }

    public boolean supportsSchemasInTableDefinitions() throws SQLException {
        return realMetadata.supportsSchemasInTableDefinitions();
    }

    public boolean supportsSelectForUpdate() throws SQLException {
        return realMetadata.supportsSelectForUpdate();
    }

    public boolean supportsStoredProcedures() throws SQLException {
        return realMetadata.supportsStoredProcedures();
    }

    public boolean supportsSubqueriesInComparisons() throws SQLException {
        return realMetadata.supportsSubqueriesInComparisons();
    }

    public boolean supportsSubqueriesInExists() throws SQLException {
        return realMetadata.supportsSubqueriesInExists();
    }

    public boolean supportsSubqueriesInIns() throws SQLException {
        return realMetadata.supportsSubqueriesInIns();
    }

    public boolean supportsSubqueriesInQuantifieds() throws SQLException {
        return realMetadata.supportsSubqueriesInQuantifieds();
    }

    public boolean supportsTableCorrelationNames() throws SQLException {
        return realMetadata.supportsTableCorrelationNames();
    }

    public boolean supportsTransactionIsolationLevel(int level)
            throws SQLException {
        return realMetadata.supportsTransactionIsolationLevel(level);
    }

    public boolean supportsTransactions() throws SQLException {
        return realMetadata.supportsTransactions();
    }

    public boolean supportsUnion() throws SQLException {
        return realMetadata.supportsUnion();
    }

    public boolean supportsUnionAll() throws SQLException {
        return realMetadata.supportsUnionAll();
    }

    public boolean updatesAreDetected(int type) throws SQLException {
        return realMetadata.updatesAreDetected(type);
    }

    public boolean usesLocalFilePerTable() throws SQLException {
        return realMetadata.usesLocalFilePerTable();
    }

    public boolean usesLocalFiles() throws SQLException {
        return realMetadata.usesLocalFiles();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.DatabaseMetaData#supportsSavepoints()
     */
    public boolean supportsSavepoints() throws SQLException {
        return realMetadata.supportsSavepoints();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.DatabaseMetaData#supportsNamedParameters()
     */
    public boolean supportsNamedParameters() throws SQLException {
        return realMetadata.supportsNamedParameters();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.DatabaseMetaData#supportsMultipleOpenResults()
     */
    public boolean supportsMultipleOpenResults() throws SQLException {
        return realMetadata.supportsMultipleOpenResults();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.DatabaseMetaData#supportsGetGeneratedKeys()
     */
    public boolean supportsGetGeneratedKeys() throws SQLException {
        return realMetadata.supportsGetGeneratedKeys();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.DatabaseMetaData#getSuperTypes(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public ResultSet getSuperTypes(String arg0, String arg1, String arg2)
            throws SQLException {
        return realMetadata.getSuperTypes(arg0, arg1, arg2);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.DatabaseMetaData#getSuperTables(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public ResultSet getSuperTables(String arg0, String arg1, String arg2)
            throws SQLException {
        return realMetadata.getSuperTables(arg0, arg1, arg2);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.DatabaseMetaData#getAttributes(java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    public ResultSet getAttributes(String arg0, String arg1, String arg2,
            String arg3) throws SQLException {
        return realMetadata.getAttributes(arg0, arg1, arg2, arg3);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.DatabaseMetaData#supportsResultSetHoldability(int)
     */
    public boolean supportsResultSetHoldability(int arg0) throws SQLException {
        return realMetadata.supportsResultSetHoldability(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.DatabaseMetaData#getResultSetHoldability()
     */
    public int getResultSetHoldability() throws SQLException {
        return realMetadata.getResultSetHoldability();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.DatabaseMetaData#getDatabaseMajorVersion()
     */
    public int getDatabaseMajorVersion() throws SQLException {
        return realMetadata.getDatabaseMajorVersion();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.DatabaseMetaData#getDatabaseMinorVersion()
     */
    public int getDatabaseMinorVersion() throws SQLException {
        return realMetadata.getDatabaseMinorVersion();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.DatabaseMetaData#getJDBCMajorVersion()
     */
    public int getJDBCMajorVersion() throws SQLException {
        return realMetadata.getJDBCMajorVersion();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.DatabaseMetaData#getJDBCMinorVersion()
     */
    public int getJDBCMinorVersion() throws SQLException {
        return realMetadata.getJDBCMinorVersion();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.DatabaseMetaData#getSQLStateType()
     */
    public int getSQLStateType() throws SQLException {
        return realMetadata.getSQLStateType();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.DatabaseMetaData#locatorsUpdateCopy()
     */
    public boolean locatorsUpdateCopy() throws SQLException {
        return realMetadata.locatorsUpdateCopy();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.DatabaseMetaData#supportsStatementPooling()
     */
    public boolean supportsStatementPooling() throws SQLException {
        return realMetadata.supportsStatementPooling();
    }

    public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
        return realMetadata.autoCommitFailureClosesAllResultSets();
    }

    public ResultSet getClientInfoProperties() throws SQLException {
        return realMetadata.getClientInfoProperties();
    }

    public ResultSet getFunctionColumns(String arg0, String arg1, String arg2, String arg3) throws SQLException {
        return realMetadata.getFunctionColumns(arg0, arg1, arg2, arg3);
    }

    public ResultSet getFunctions(String arg0, String arg1, String arg2) throws SQLException {
        return realMetadata.getFunctions(arg0, arg1, arg2);
    }

    public RowIdLifetime getRowIdLifetime() throws SQLException {
        return realMetadata.getRowIdLifetime();
    }

    public ResultSet getSchemas(String arg0, String arg1) throws SQLException {
        return realMetadata.getSchemas(arg0, arg1);
    }

    public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
        return realMetadata.supportsStoredFunctionsUsingCallSyntax();
    }

    public boolean isWrapperFor(Class arg0) throws SQLException {
        return realMetadata.isWrapperFor(arg0);
    }

    public Object unwrap(Class arg0) throws SQLException {
        return realMetadata.unwrap(arg0);
    }

}

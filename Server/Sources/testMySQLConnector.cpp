/*
* This file is part of Wakanda software, licensed by 4D under
*  (i) the GNU General Public License version 3 (GNU GPL v3), or
*  (ii) the Affero General Public License version 3 (AGPL v3) or
*  (iii) a commercial license.
* This file remains the exclusive property of 4D and/or its licensors
* and is protected by national and international legislations.
* In any event, Licensee's compliance with the terms and conditions
* of the applicable license constitutes a prerequisite to any use of this file.
* Except as otherwise expressly stated in the applicable license,
* such license does not include any other license or rights on this file,
* 4D's and/or its licensors' trademarks and/or other proprietary rights.
* Consequently, no title, copyright or other proprietary rights
* other than those specified in the applicable license is granted.
*/

#include "headers4d.h"

#include "testMySQLConnector.h"

#include "../../../../Components/Main/SQLConnectorCommon/Headers/ISQLSession.h"
#include "../../../../Components/Main/SQLConnectorCommon/Headers/ISQLResultSet.h"
#include "../../../../Components/Main/SQLConnectorCommon/Headers/ISQLRow.h"
#include "../../../../Components/Main/SQLConnectorCommon/Headers/ISQLStatement.h"
#include "../../../../Components/Main/SQLConnectorCommon/Headers/ISQLPreparedStatement.h"

#include "../../../../Components/Main/MySQLConnector/Headers/CMySQLConnector.h"



USING_TOOLBOX_NAMESPACE


#define MYSQL_HOST					( VJSONValue( "192.168.222.12" ) )

#define MYSQL_USER					( VJSONValue( "abdessamad" ) )

#define MYSQL_CORRECT_PASSWORD		( VJSONValue( "secret" ) )

#define MYSQL_INCORRECT_PASSWORD	( VJSONValue( "!secret" ) )

#define MYSQL_DATABASE				( VJSONValue( "benchdb" ) )

#define MYSQL_PORT					( VJSONValue( 3306 ) )

#define MYSQL_SSL_TRUE				( VJSONValue( JSON_true ) )

#define MYSQL_SSL_FALSE				( VJSONValue( JSON_false ) )


void testMySQLConnectorCreateSessionWithValidParams()
{
	CMySQLConnector *mysqlConnector = VComponentManager::RetainComponent<CMySQLConnector>();

	VJSONObject * params = new VJSONObject();

	params->SetProperty( "hostname",	MYSQL_HOST );

	params->SetProperty( "user",		MYSQL_USER );

	params->SetProperty( "password",	MYSQL_CORRECT_PASSWORD );

	params->SetProperty( "database",	MYSQL_DATABASE );

	params->SetProperty( "port",		MYSQL_PORT );

	params->SetProperty( "ssl",			MYSQL_SSL_FALSE );

	ISQLSession *session = mysqlConnector->CreateSession( params );

	if( session != NULL )
	{
		printf( "connection to mysql server was successful ..\n" );

		ReleaseRefCountable( &session );
	}
	else
	{
		printf( "connection to mysql server failed ..\n" );
	}

	ReleaseRefCountable( &params );

	ReleaseRefCountable( &mysqlConnector );
}

void testMySQLConnectorCreateSessionWithInCorrectPassword()
{
	CMySQLConnector *mysqlConnector = VComponentManager::RetainComponent<CMySQLConnector>();

	VJSONObject * params = new VJSONObject();

	params->SetProperty( "hostname",	MYSQL_HOST );

	params->SetProperty( "user",		MYSQL_USER );

	params->SetProperty( "password",	MYSQL_INCORRECT_PASSWORD );

	params->SetProperty( "database",	MYSQL_DATABASE );

	params->SetProperty( "port",		MYSQL_PORT );

	params->SetProperty( "ssl",			MYSQL_SSL_FALSE );

	ISQLSession *session = mysqlConnector->CreateSession( params );

	if( session != NULL )
	{
		printf( "connection to mysql server was successful ..\n" );

		ReleaseRefCountable( &session );
	}
	else
	{
		printf( "connection to mysql server failed ..\n" );
	}

	ReleaseRefCountable( &params );

	ReleaseRefCountable( &mysqlConnector );
}


void testMySQLConnectorExecuteQuerySelect()
{
	CMySQLConnector *mysqlConnector = VComponentManager::RetainComponent<CMySQLConnector>();

	VJSONObject * params = new VJSONObject();

	params->SetProperty( "hostname",	MYSQL_HOST );

	params->SetProperty( "user",		MYSQL_USER );

	params->SetProperty( "password",	MYSQL_CORRECT_PASSWORD );

	params->SetProperty( "database",	MYSQL_DATABASE );

	params->SetProperty( "port",		MYSQL_PORT );

	params->SetProperty( "ssl",			MYSQL_SSL_FALSE );

	ISQLSession *session = mysqlConnector->CreateSession( params );

	if(session != NULL)
	{
		VString query("SELECT * FROM people WHERE id < 10");

		VError error = session->ExecuteQuery( query );

		if( error == VE_OK )
		{
			sLONG count = session->GetResultSetsCount();

			for(sLONG i = 0; i < count; ++i)
			{
				ISQLResultSet* res = session->RetainNthResultSet( i );

				//the result set contains error
				if(res->IsError())
				{
					VString errMsg = res->GetErrorMessage();
					//do the processing in case of error
				}
				else
				{
					while( !res->IsEOF() )
					{
						ISQLRow *row = res->RetainNextRow();


						VValue *idValue = row->CreateNthValue( 0 );
						DebugMsg( "id = %V\n", idValue );


						VValue *firstNameValue = row->CreateNthValue( 2 );
						DebugMsg( "first name = %V\n", firstNameValue );


						VValue *dateValue = row->CreateNthValue( 4 );
						DebugMsg( "date = %V\n", dateValue );


						ReleaseRefCountable( &row );
					}
				}


				ReleaseRefCountable( &res );
			}

		}
		else
		{
			printf( "an error occured in the query ..\n" );
		}
		
		ReleaseRefCountable( &session );
	}
	else
	{
		printf( "connection to mysql server failed ..\n" );
	}

	ReleaseRefCountable( &params );

	ReleaseRefCountable( &mysqlConnector );
}

void testMySQLConnectorStatement()
{
	CMySQLConnector *mysqlConnector = VComponentManager::RetainComponent<CMySQLConnector>();

	VJSONObject * params = new VJSONObject();

	params->SetProperty( "hostname",	MYSQL_HOST );

	params->SetProperty( "user",		MYSQL_USER );

	params->SetProperty( "password",	MYSQL_CORRECT_PASSWORD );

	params->SetProperty( "database",	MYSQL_DATABASE );

	params->SetProperty( "port",		MYSQL_PORT );

	params->SetProperty( "ssl",			MYSQL_SSL_FALSE );

	ISQLSession *session = mysqlConnector->CreateSession( params );

	ReleaseRefCountable( &params );

	if(session != NULL)
	{
		ISQLStatement *stmt = session->CreateStatement( "SELECT * FROM people WHERE id < 5");

		VError error = VE_OK;

		ISQLResultSet *res = stmt->Execute( error );

		if( error == VE_OK )
		{
			if( res->IsError() )
			{
				printf( "an error occured in the execution of the prepared statement!\n" );
			}
			else
			{
				while( !res->IsEOF() )
				{
					ISQLRow *row = res->RetainNextRow();


					VValue *idValue = row->CreateNthValue( 0 );
					VString id;
					id.AppendPrintf("%V", idValue);
					DebugMsg( "id = %V\n", &id);


					VValue *firstNameValue = row->CreateNthValue( 2 );
					VString firstName;
					firstName.AppendPrintf("%V", firstNameValue);
					DebugMsg( "first name = %V\n", &firstName );


					VValue *dateValue = row->CreateNthValue( 4 );
					VString date;
					date.AppendPrintf("%V", dateValue);
					DebugMsg( "date = %V\n", &date );


					ReleaseRefCountable( &row );
				}
			}

			ReleaseRefCountable( &res );
		}
		else
		{
			printf( "an error occured in the execution of the prepared statement!\n" );
		}

		ReleaseRefCountable( &session );
	}
	else
	{
		printf( "connection to mysql server failed ..\n" );
	}

	ReleaseRefCountable( &mysqlConnector );
}



void testMySQLConnectorPreparedStatementWithIntegerParam()
{
	CMySQLConnector *mysqlConnector = VComponentManager::RetainComponent<CMySQLConnector>();

	VJSONObject * params = new VJSONObject();

	params->SetProperty( "hostname",	MYSQL_HOST );

	params->SetProperty( "user",		MYSQL_USER );

	params->SetProperty( "password",	MYSQL_CORRECT_PASSWORD );

	params->SetProperty( "database",	MYSQL_DATABASE );

	params->SetProperty( "port",		MYSQL_PORT );

	params->SetProperty( "ssl",			MYSQL_SSL_FALSE );

	ISQLSession *session = mysqlConnector->CreateSession( params );

	ReleaseRefCountable( &params );

	if(session != NULL)
	{
		ISQLStatement *statement = session->CreateStatement( "SELECT * FROM people WHERE id = ?");

		VError error = VE_OK;

		ISQLPreparedStatement* pStmt = statement->CreatePreparedStatement(error);

		VLong keyLong(3);

		pStmt->SetNthParameter( 1, keyLong );

		ISQLResultSet *res = pStmt->Execute( error );

		if( error == VE_OK )
		{
			if( res->IsError() )
			{
				printf( "an error occured in the execution of the prepared statement!\n" );
			}
			else
			{
				while( !res->IsEOF() )
				{
					ISQLRow *row = res->RetainNextRow();

					VValue *idValue = row->CreateNthValue( 0 );

					VValue *firstNameValue = row->CreateNthValue( 2 );

					VValue *dateValue = row->CreateNthValue( 4 );

					VString DbgMsg;

					DbgMsg.AppendPrintf( "idValue = %V, firstNameValue = %V, dateValue = %V", idValue, firstNameValue, dateValue );

					DebugMsg( "%V", &DbgMsg );

					ReleaseRefCountable( &row );
				}
			}

			ReleaseRefCountable( &res );
		}
		else
		{
			printf( "an error occured in the execution of the prepared statement!\n" );
		}

		ReleaseRefCountable( &session );
	}
	else
	{
		printf( "connection to mysql server failed ..\n" );
	}

	ReleaseRefCountable( &mysqlConnector );

}


void testMySQLConnectorPreparedStatementWithStringParam()
{
	CMySQLConnector *mysqlConnector = VComponentManager::RetainComponent<CMySQLConnector>();

	VJSONObject * params = new VJSONObject();

	params->SetProperty( "hostname",	MYSQL_HOST );

	params->SetProperty( "user",		MYSQL_USER );

	params->SetProperty( "password",	MYSQL_CORRECT_PASSWORD );

	params->SetProperty( "database",	MYSQL_DATABASE );

	params->SetProperty( "port",		MYSQL_PORT );

	params->SetProperty( "ssl",			MYSQL_SSL_FALSE );

	ISQLSession *session = mysqlConnector->CreateSession( params );

	ReleaseRefCountable( &params );

	if(session != NULL)
	{
		ISQLStatement *statement = session->CreateStatement( "SELECT * FROM people WHERE first_name = ?");

		VError error = VE_OK;

		ISQLPreparedStatement* pStmt = statement->CreatePreparedStatement(error);

		VString keyString("sotyi");

		pStmt->SetNthParameter( 1, keyString );

		ISQLResultSet *res = pStmt->Execute( error );

		if( error == VE_OK )
		{
			if( res->IsError() )
			{
				printf( "an error occured in the execution of the prepared statement!\n" );
			}
			else
			{
				while( !res->IsEOF() )
				{
					ISQLRow *row = res->RetainNextRow();

					VValue *idValue = row->CreateNthValue( 0 );

					VValue *firstNameValue = row->CreateNthValue( 2 );

					VValue *dateValue = row->CreateNthValue( 4 );

					VString DbgMsg;

					DbgMsg.AppendPrintf( "idValue = %V, firstNameValue = %V, dateValue = %V", idValue, firstNameValue, dateValue );

					DebugMsg( "%V", &DbgMsg );

					ReleaseRefCountable( &row );
				}
			}

			ReleaseRefCountable( &res );
		}
		else
		{
			printf( "an error occured in the execution of the prepared statement!\n" );
		}

		ReleaseRefCountable( &session );
	}
	else
	{
		printf( "connection to mysql server failed ..\n" );
	}

	ReleaseRefCountable( &mysqlConnector );

}

void testMySQLConnectorPreparedStatementWithDateParam()
{
	CMySQLConnector *mysqlConnector = VComponentManager::RetainComponent<CMySQLConnector>();

	VJSONObject * params = new VJSONObject();

	params->SetProperty( "hostname",	MYSQL_HOST );

	params->SetProperty( "user",		MYSQL_USER );

	params->SetProperty( "password",	MYSQL_CORRECT_PASSWORD );

	params->SetProperty( "database",	MYSQL_DATABASE );

	params->SetProperty( "port",		MYSQL_PORT );

	params->SetProperty( "ssl",			MYSQL_SSL_FALSE );

	ISQLSession *session = mysqlConnector->CreateSession( params );

	ReleaseRefCountable( &params );

	if(session != NULL)
	{
		ISQLStatement *statement = session->CreateStatement( "SELECT * FROM people WHERE date_of_birth = ?");

		VError error = VE_OK;

		ISQLPreparedStatement* pStmt = statement->CreatePreparedStatement(error);

		VTime keyDate;

		keyDate.FromUTCTime( 1984, 8, 11, 0, 0, 0, 0 );

		pStmt->SetNthParameter( 1, keyDate );

		ISQLResultSet *res = pStmt->Execute( error );

		if( error == VE_OK )
		{
			if( res->IsError() )
			{
				printf( "an error occured in the execution of the prepared statement!\n" );

				VString msg = res->GetErrorMessage();

				printf( "error msg = %V\n", &msg );
			}
			else
			{
				while( !res->IsEOF() )
				{
					ISQLRow *row = res->RetainNextRow();

					VValue *idValue = row->CreateNthValue( 0 );

					VValue *firstNameValue = row->CreateNthValue( 2 );

					VValue *dateValue = row->CreateNthValue( 4 );

					VString DbgMsg;

					DbgMsg.AppendPrintf( "idValue = %V, firstNameValue = %V, dateValue = %V", idValue, firstNameValue, dateValue );

					DebugMsg( "%V", &DbgMsg );

					ReleaseRefCountable( &row );
				}
			}

			ReleaseRefCountable( &res );
		}
		else
		{
			printf( "an error occured in the execution of the prepared statement!\n" );
		}

		ReleaseRefCountable( &session );
	}
	else
	{
		printf( "connection to mysql server failed ..\n" );
	}

	ReleaseRefCountable( &mysqlConnector );

}


void testMySQLConnectorPreparedStatementWithTypeTinyInt()
{
	CMySQLConnector *mysqlConnector = VComponentManager::RetainComponent<CMySQLConnector>();

	VJSONObject * params = new VJSONObject();

	params->SetProperty( "hostname",	MYSQL_HOST );

	params->SetProperty( "user",		MYSQL_USER );

	params->SetProperty( "password",	MYSQL_CORRECT_PASSWORD );

	params->SetProperty( "database",	MYSQL_DATABASE );

	params->SetProperty( "port",		MYSQL_PORT );

	params->SetProperty( "ssl",			MYSQL_SSL_FALSE );

	ISQLSession *session = mysqlConnector->CreateSession( params );

	ReleaseRefCountable( &params );

	if(session != NULL)
	{
		ISQLStatement *statement = session->CreateStatement( "SELECT * FROM test_tiny_int");

		VError error = VE_OK;

		ISQLPreparedStatement* pStmt = statement->CreatePreparedStatement(error);

		ISQLResultSet *res = pStmt->Execute( error );

		if( error == VE_OK )
		{
			if( res->IsError() )
			{
				printf( "an error occured in the execution of the prepared statement!\n" );
			}
			else
			{
				while( !res->IsEOF() )
				{
					ISQLRow *row = res->RetainNextRow();

					VValue* value = row->CreateNthValue( 1 );

					VString dbgMsg;

					dbgMsg.AppendPrintf( "value = %V", value );

					DebugMsg( "%V\n", &dbgMsg );

					ReleaseRefCountable( &row );
				}
			}

			ReleaseRefCountable( &res );
		}
		else
		{
			printf( "an error occured in the execution of the prepared statement!\n" );
		}

		ReleaseRefCountable( &session );
	}
	else
	{
		printf( "connection to mysql server failed ..\n" );
	}

	ReleaseRefCountable( &mysqlConnector );
}


void testMySQLConnectorPreparedStatementWithTypeSmallInt()
{
	CMySQLConnector *mysqlConnector = VComponentManager::RetainComponent<CMySQLConnector>();

	VJSONObject * params = new VJSONObject();

	params->SetProperty( "hostname",	MYSQL_HOST );

	params->SetProperty( "user",		MYSQL_USER );

	params->SetProperty( "password",	MYSQL_CORRECT_PASSWORD );

	params->SetProperty( "database",	MYSQL_DATABASE );

	params->SetProperty( "port",		MYSQL_PORT );

	params->SetProperty( "ssl",			MYSQL_SSL_FALSE );

	ISQLSession *session = mysqlConnector->CreateSession( params );

	ReleaseRefCountable( &params );

	if(session != NULL)
	{
		ISQLStatement *statement = session->CreateStatement( "SELECT * FROM test_small_int");

		VError error = VE_OK;

		ISQLPreparedStatement* pStmt = statement->CreatePreparedStatement(error);

		ISQLResultSet *res = pStmt->Execute( error );

		if( error == VE_OK )
		{
			if( res->IsError() )
			{
				printf( "an error occured in the execution of the prepared statement!\n" );
			}
			else
			{
				while( !res->IsEOF() )
				{
					ISQLRow *row = res->RetainNextRow();

					VValue* value = row->CreateNthValue( 1 );

					VString dbgMsg;

					dbgMsg.AppendPrintf( "value = %V", value );

					DebugMsg( "%V\n", &dbgMsg );

					ReleaseRefCountable( &row );
				}
			}

			ReleaseRefCountable( &res );
		}
		else
		{
			printf( "an error occured in the execution of the prepared statement!\n" );
		}

		ReleaseRefCountable( &session );
	}
	else
	{
		printf( "connection to mysql server failed ..\n" );
	}

	ReleaseRefCountable( &mysqlConnector );
}


void testMySQLConnectorPreparedStatementWithTypeMediumInt()
{
	CMySQLConnector *mysqlConnector = VComponentManager::RetainComponent<CMySQLConnector>();

	VJSONObject * params = new VJSONObject();

	params->SetProperty( "hostname",	MYSQL_HOST );

	params->SetProperty( "user",		MYSQL_USER );

	params->SetProperty( "password",	MYSQL_CORRECT_PASSWORD );

	params->SetProperty( "database",	MYSQL_DATABASE );

	params->SetProperty( "port",		MYSQL_PORT );

	params->SetProperty( "ssl",			MYSQL_SSL_FALSE );

	ISQLSession *session = mysqlConnector->CreateSession( params );

	ReleaseRefCountable( &params );

	if(session != NULL)
	{
		ISQLStatement *statement = session->CreateStatement( "SELECT * FROM test_medium_int");

		VError error = VE_OK;

		ISQLPreparedStatement* pStmt = statement->CreatePreparedStatement(error);

		ISQLResultSet *res = pStmt->Execute( error );

		if( error == VE_OK )
		{
			if( res->IsError() )
			{
				printf( "an error occured in the execution of the prepared statement!\n" );
			}
			else
			{
				while( !res->IsEOF() )
				{
					ISQLRow *row = res->RetainNextRow();

					VValue* value = row->CreateNthValue( 1 );

					VString dbgMsg;

					dbgMsg.AppendPrintf( "value = %V", value );

					DebugMsg( "%V\n", &dbgMsg );

					ReleaseRefCountable( &row );
				}
			}

			ReleaseRefCountable( &res );
		}
		else
		{
			printf( "an error occured in the execution of the prepared statement!\n" );
		}

		ReleaseRefCountable( &session );
	}
	else
	{
		printf( "connection to mysql server failed ..\n" );
	}

	ReleaseRefCountable( &mysqlConnector );
}


void testMySQLConnectorPreparedStatementWithTypeInt()
{
	CMySQLConnector *mysqlConnector = VComponentManager::RetainComponent<CMySQLConnector>();

	VJSONObject * params = new VJSONObject();

	params->SetProperty( "hostname",	MYSQL_HOST );

	params->SetProperty( "user",		MYSQL_USER );

	params->SetProperty( "password",	MYSQL_CORRECT_PASSWORD );

	params->SetProperty( "database",	MYSQL_DATABASE );

	params->SetProperty( "port",		MYSQL_PORT );

	params->SetProperty( "ssl",			MYSQL_SSL_FALSE );

	ISQLSession *session = mysqlConnector->CreateSession( params );

	ReleaseRefCountable( &params );

	if(session != NULL)
	{
		ISQLStatement *statement = session->CreateStatement( "SELECT * FROM test_int");

		VError error = VE_OK;

		ISQLPreparedStatement* pStmt = statement->CreatePreparedStatement(error);

		ISQLResultSet *res = pStmt->Execute( error );

		if( error == VE_OK )
		{
			if( res->IsError() )
			{
				printf( "an error occured in the execution of the prepared statement!\n" );
			}
			else
			{
				while( !res->IsEOF() )
				{
					ISQLRow *row = res->RetainNextRow();

					VValue* value = row->CreateNthValue( 1 );

					VString dbgMsg;

					dbgMsg.AppendPrintf( "value = %V", value );

					DebugMsg( "%V\n", &dbgMsg );

					ReleaseRefCountable( &row );
				}
			}

			ReleaseRefCountable( &res );
		}
		else
		{
			printf( "an error occured in the execution of the prepared statement!\n" );
		}
		
		ReleaseRefCountable( &session );
	}
	else
	{
		printf( "connection to mysql server failed ..\n" );
	}

	ReleaseRefCountable( &mysqlConnector );
}

void testMySQLConnectorPreparedStatementWithTypeBigInt()
{
	CMySQLConnector *mysqlConnector = VComponentManager::RetainComponent<CMySQLConnector>();

	VJSONObject * params = new VJSONObject();

	params->SetProperty( "hostname",	MYSQL_HOST );

	params->SetProperty( "user",		MYSQL_USER );

	params->SetProperty( "password",	MYSQL_CORRECT_PASSWORD );

	params->SetProperty( "database",	MYSQL_DATABASE );

	params->SetProperty( "port",		MYSQL_PORT );

	params->SetProperty( "ssl",			MYSQL_SSL_FALSE );

	ISQLSession *session = mysqlConnector->CreateSession( params );

	ReleaseRefCountable( &params );

	if(session != NULL)
	{
		ISQLStatement *statement = session->CreateStatement( "SELECT * FROM test_big_int");

		VError error = VE_OK;

		ISQLPreparedStatement* pStmt = statement->CreatePreparedStatement(error);

		ISQLResultSet *res = pStmt->Execute( error );

		if( error == VE_OK )
		{
			if( res->IsError() )
			{
				printf( "an error occured in the execution of the prepared statement!\n" );
			}
			else
			{
				while( !res->IsEOF() )
				{
					ISQLRow *row = res->RetainNextRow();

					VValue* value = row->CreateNthValue( 1 );

					VString dbgMsg;

					dbgMsg.AppendPrintf( "value = %V", value );

					DebugMsg( "%V\n", &dbgMsg );

					ReleaseRefCountable( &row );
				}
			}

			ReleaseRefCountable( &res );
		}
		else
		{
			printf( "an error occured in the execution of the prepared statement!\n" );
		}
		
		ReleaseRefCountable( &session );
	}
	else
	{
		printf( "connection to mysql server failed ..\n" );
	}

	ReleaseRefCountable( &mysqlConnector );
}

void testMySQLConnectorPreparedStatementWithTypeFloat()
{
	CMySQLConnector *mysqlConnector = VComponentManager::RetainComponent<CMySQLConnector>();

	VJSONObject * params = new VJSONObject();

	params->SetProperty( "hostname",	MYSQL_HOST );

	params->SetProperty( "user",		MYSQL_USER );

	params->SetProperty( "password",	MYSQL_CORRECT_PASSWORD );

	params->SetProperty( "database",	MYSQL_DATABASE );

	params->SetProperty( "port",		MYSQL_PORT );

	params->SetProperty( "ssl",			MYSQL_SSL_FALSE );

	ISQLSession *session = mysqlConnector->CreateSession( params );

	ReleaseRefCountable( &params );

	if(session != NULL)
	{
		ISQLStatement *statement = session->CreateStatement( "SELECT * FROM test_float");

		VError error = VE_OK;

		ISQLPreparedStatement* pStmt = statement->CreatePreparedStatement(error);

		ISQLResultSet *res = pStmt->Execute( error );

		if( error == VE_OK )
		{
			if( res->IsError() )
			{
				printf( "an error occured in the execution of the prepared statement!\n" );
			}
			else
			{
				while( !res->IsEOF() )
				{
					ISQLRow *row = res->RetainNextRow();

					VValue* value = row->CreateNthValue( 1 );

					VString dbgMsg;

					dbgMsg.AppendPrintf( "value = %V", value );

					DebugMsg( "%V\n", &dbgMsg );

					ReleaseRefCountable( &row );
				}
			}

			ReleaseRefCountable( &res );
		}
		else
		{
			printf( "an error occured in the execution of the prepared statement!\n" );
		}
		
		ReleaseRefCountable( &session );
	}
	else
	{
		printf( "connection to mysql server failed ..\n" );
	}

	ReleaseRefCountable( &mysqlConnector );
}

void testMySQLConnectorPreparedStatementWithTypeDouble()
{
	CMySQLConnector *mysqlConnector = VComponentManager::RetainComponent<CMySQLConnector>();

	VJSONObject * params = new VJSONObject();

	params->SetProperty( "hostname",	MYSQL_HOST );

	params->SetProperty( "user",		MYSQL_USER );

	params->SetProperty( "password",	MYSQL_CORRECT_PASSWORD );

	params->SetProperty( "database",	MYSQL_DATABASE );

	params->SetProperty( "port",		MYSQL_PORT );

	params->SetProperty( "ssl",			MYSQL_SSL_FALSE );

	ISQLSession *session = mysqlConnector->CreateSession( params );

	ReleaseRefCountable( &params );

	if(session != NULL)
	{
		ISQLStatement *statement = session->CreateStatement( "SELECT * FROM test_double");

		VError error = VE_OK;

		ISQLPreparedStatement* pStmt = statement->CreatePreparedStatement(error);

		ISQLResultSet *res = pStmt->Execute( error );

		if( error == VE_OK )
		{
			if( res->IsError() )
			{
				printf( "an error occured in the execution of the prepared statement!\n" );
			}
			else
			{
				while( !res->IsEOF() )
				{
					ISQLRow *row = res->RetainNextRow();

					VValue* value = row->CreateNthValue( 1 );

					VString dbgMsg;

					dbgMsg.AppendPrintf( "value = %V", value );

					DebugMsg( "%V\n", &dbgMsg );

					ReleaseRefCountable( &row );
				}
			}

			ReleaseRefCountable( &res );
		}
		else
		{
			printf( "an error occured in the execution of the prepared statement!\n" );
		}
		
		ReleaseRefCountable( &session );
	}
	else
	{
		printf( "connection to mysql server failed ..\n" );
	}

	ReleaseRefCountable( &mysqlConnector );
}


void testMySQLConnectorPreparedStatementWithTypeString()
{
	CMySQLConnector *mysqlConnector = VComponentManager::RetainComponent<CMySQLConnector>();

	VJSONObject * params = new VJSONObject();

	params->SetProperty( "hostname",	MYSQL_HOST );

	params->SetProperty( "user",		MYSQL_USER );

	params->SetProperty( "password",	MYSQL_CORRECT_PASSWORD );

	params->SetProperty( "database",	MYSQL_DATABASE );

	params->SetProperty( "port",		MYSQL_PORT );

	params->SetProperty( "ssl",			MYSQL_SSL_FALSE );

	ISQLSession *session = mysqlConnector->CreateSession( params );

	ReleaseRefCountable( &params );

	if(session != NULL)
	{
		ISQLStatement *statement = session->CreateStatement( "SELECT * FROM test_string");

		VError error = VE_OK;

		ISQLPreparedStatement* pStmt = statement->CreatePreparedStatement(error);

		ISQLResultSet *res = pStmt->Execute( error );

		if( error == VE_OK )
		{
			if( res->IsError() )
			{
				printf( "an error occured in the execution of the prepared statement!\n" );
			}
			else
			{
				while( !res->IsEOF() )
				{
					ISQLRow *row = res->RetainNextRow();

					VValue* value = row->CreateNthValue( 1 );

					VString dbgMsg;

					dbgMsg.AppendPrintf( "value = %V", value );

					DebugMsg( "%V\n", &dbgMsg );

					ReleaseRefCountable( &row );
				}
			}

			ReleaseRefCountable( &res );
		}
		else
		{
			printf( "an error occured in the execution of the prepared statement!\n" );
		}
		
		ReleaseRefCountable( &session );
	}
	else
	{
		printf( "connection to mysql server failed ..\n" );
	}

	ReleaseRefCountable( &mysqlConnector );
}


void testMySQLConnectorPreparedStatementWithTypeDateTime()
{
	CMySQLConnector *mysqlConnector = VComponentManager::RetainComponent<CMySQLConnector>();

	VJSONObject * params = new VJSONObject();

	params->SetProperty( "hostname",	MYSQL_HOST );

	params->SetProperty( "user",		MYSQL_USER );

	params->SetProperty( "password",	MYSQL_CORRECT_PASSWORD );

	params->SetProperty( "database",	MYSQL_DATABASE );

	params->SetProperty( "port",		MYSQL_PORT );

	params->SetProperty( "ssl",			MYSQL_SSL_FALSE );

	ISQLSession *session = mysqlConnector->CreateSession( params );

	ReleaseRefCountable( &params );

	if(session != NULL)
	{
		ISQLStatement *statement = session->CreateStatement( "SELECT * FROM test_datetime");

		VError error = VE_OK;

		ISQLPreparedStatement* pStmt = statement->CreatePreparedStatement(error);

		ISQLResultSet *res = pStmt->Execute( error );

		if( error == VE_OK )
		{
			if( res->IsError() )
			{
				printf( "an error occured in the execution of the prepared statement!\n" );
			}
			else
			{
				while( !res->IsEOF() )
				{
					ISQLRow *row = res->RetainNextRow();

					VValue* value = row->CreateNthValue( 1 );

					VString dbgMsg;

					dbgMsg.AppendPrintf( "value = %V", value );

					DebugMsg( "%V\n", &dbgMsg );

					ReleaseRefCountable( &row );
				}
			}

			ReleaseRefCountable( &res );
		}
		else
		{
			printf( "an error occured in the execution of the prepared statement!\n" );
		}
		
		ReleaseRefCountable( &session );
	}
	else
	{
		printf( "connection to mysql server failed ..\n" );
	}

	ReleaseRefCountable( &mysqlConnector );
}


void testMySQLConnectorPreparedStatementWithTypeBlob()
{
	CMySQLConnector *mysqlConnector = VComponentManager::RetainComponent<CMySQLConnector>();

	VJSONObject * params = new VJSONObject();

	params->SetProperty( "hostname",	MYSQL_HOST );

	params->SetProperty( "user",		MYSQL_USER );

	params->SetProperty( "password",	MYSQL_CORRECT_PASSWORD );

	params->SetProperty( "database",	MYSQL_DATABASE );

	params->SetProperty( "port",		MYSQL_PORT );

	params->SetProperty( "ssl",			MYSQL_SSL_FALSE );

	ISQLSession *session = mysqlConnector->CreateSession( params );

	ReleaseRefCountable( &params );

	if(session != NULL)
	{
		ISQLStatement *statement = session->CreateStatement( "SELECT * FROM test_blob");

		VError error = VE_OK;

		ISQLPreparedStatement* pStmt = statement->CreatePreparedStatement(error);

		ISQLResultSet *res = pStmt->Execute( error );

		if( error == VE_OK )
		{
			if( res->IsError() )
			{
				printf( "an error occured in the execution of the prepared statement!\n" );
			}
			else
			{
				uBYTE expected[3][16] = {
					{0x0f, 0xC9, 0xCB, 0xBB, 0xCC, 0xCE, 0xB9, 0xC8, 0xCA, 0xBC, 0xCC, 0xCE, 0xB9, 0xC9, 0xCB, 0xBB},
					{0x03, 0xAB, 0xCD, 0xEF},
					{0x00}
				};

				sLONG i = 0;

				bool ok = true;

				while( !res->IsEOF() )
				{
					ISQLRow *row = res->RetainNextRow();

					VValue *value = row->CreateNthValue( 1 );

					VBlobWithPtr *blobValue = static_cast<VBlobWithPtr*>(value);

					sLONG8 len = blobValue->GetSize();

				

					if( len != expected[i][0] )
					{
						ok = false;
					}
					else if( len == 0 )
					{

					}
					else
					{
						uBYTE *buffer = (uBYTE*) ::malloc( len );

						xbox::VSize copied;

						blobValue->GetData( buffer, len, 0, &copied );

						for( sLONG n = 0; n < len; ++n )
						{
							if( expected[i][n+1] != buffer[n] )
							{
								ok = false;
								
								break;
							}
						}
					}


					ReleaseRefCountable( &row );

					++i;
				}

				if(ok)
				{
					printf("testMySQLConnectorPreparedStatementWithTypeBlob is OK\n");
				}
				else
				{
					printf("testMySQLConnectorPreparedStatementWithTypeBlob is KO\n");
				}
			}

			ReleaseRefCountable( &res );
		}
		else
		{
			printf( "an error occured in the execution of the prepared statement!\n" );
		}
		
		ReleaseRefCountable( &session );
	}
	else
	{
		printf( "connection to mysql server failed ..\n" );
	}

	ReleaseRefCountable( &mysqlConnector );
}

void MySQLConnectorBench()
{
	CMySQLConnector *mysqlConnector = VComponentManager::RetainComponent<CMySQLConnector>();

	VJSONObject * params = new VJSONObject();

	params->SetProperty( "hostname",	MYSQL_HOST );

	params->SetProperty( "user",		MYSQL_USER );

	params->SetProperty( "password",	MYSQL_CORRECT_PASSWORD );

	params->SetProperty( "database",	MYSQL_DATABASE );

	params->SetProperty( "port",		MYSQL_PORT );

	params->SetProperty( "ssl",			MYSQL_SSL_FALSE );

	ISQLSession *session = mysqlConnector->CreateSession( params );

	if(session != NULL)
	{
		VString query("SELECT * FROM people");

		clock_t startClock = clock();

		VError error = session->ExecuteQuery( query );

		clock_t endClock = clock();

		sLONG Dt = 1000 * (endClock-startClock) / CLOCKS_PER_SEC;

		printf( "fetching all rows takes %d milliseconds\n", Dt );
		fflush(stderr);

		startClock = clock();

		ReleaseRefCountable( &session );

		endClock = clock();

		Dt = 1000 * (endClock-startClock) / CLOCKS_PER_SEC;

		printf( "releasing all rows takes %d milliseconds\n", Dt );
		fflush(stderr);
	}

	ReleaseRefCountable( &params );

	ReleaseRefCountable( &mysqlConnector );


}

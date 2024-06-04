/**
 * [BoxLang]
 *
 * Copyright [2023] [Ortus Solutions, Corp]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ortus.boxlang.modules.compat.bifs.system;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ortus.boxlang.runtime.BoxRuntime;
import ortus.boxlang.runtime.context.IBoxContext;
import ortus.boxlang.runtime.context.ScriptingRequestBoxContext;
import ortus.boxlang.runtime.scopes.IScope;
import ortus.boxlang.runtime.scopes.Key;
import ortus.boxlang.runtime.scopes.VariablesScope;
import ortus.boxlang.runtime.types.Struct;

public class GetMetadataTest {

	static BoxRuntime	instance;
	IBoxContext			context;
	IScope				variables;
	static Key			result	= new Key( "result" );

	@BeforeAll
	public static void setUp() {
		instance = BoxRuntime.getInstance( true );
	}

	@AfterAll
	public static void teardown() {

	}

	@BeforeEach
	public void setupEach() {
		context		= new ScriptingRequestBoxContext( instance.getRuntimeContext() );
		variables	= context.getScopeNearby( VariablesScope.name );
	}

	@DisplayName( "It returns meta for a BXClass" )
	@Test
	public void testItReturnsMetaFunction() {
		instance.executeSource(
		    """
		    result = getMetadata( new src.test.resources.bx.MyClass() );
		    """,
		    context );
		assertThat( variables.get( result ) ).isInstanceOf( Struct.class );
	}

	@DisplayName( "It returns meta for a CFC Class" )
	@Test
	public void testItReturnsMetaClass() {
		instance.executeSource(
		    """
		    result = getMetadata( new src.test.resources.cf.MyClassCF() );
		    """,
		    context );
		assertThat( variables.get( result ) ).isInstanceOf( Struct.class );
	}

	@DisplayName( "It returns meta for class" )
	@Test
	public void testItReturnsClassMeta() {
		instance.executeSource(
		    """
		    result = getMetadata( ()=>{} );
		    """,
		    context );
		assertThat( variables.get( result ) ).isInstanceOf( Struct.class );

	}

	@DisplayName( "It returns meta for all other objects" )
	@Test
	public void testItReturnsOtherMeta() {

		instance.executeSource(
		    """
		    result = getMetadata( {} );
		    """,
		    context );
		assertThat( variables.get( result ) ).isInstanceOf( Class.class );

		instance.executeSource(
		    """
		    result = getMetadata( [] );
		    """,
		    context );
		assertThat( variables.get( result ) ).isInstanceOf( Class.class );

		instance.executeSource(
		    """
		    result = getMetadata( "" );
		    """,
		    context );
		assertThat( variables.get( result ) ).isInstanceOf( Class.class );

		instance.executeSource(
		    """
		    result = getMetadata( 42 );
		    """,
		    context );
		assertThat( variables.get( result ) ).isInstanceOf( Class.class );

		instance.executeSource(
		    """
		    result = getMetadata( true );
		    """,
		    context );
		assertThat( variables.get( result ) ).isInstanceOf( Class.class );

	}

}

/**
 * [BoxLang]
 *
 * Copyright [2023] [Ortus Solutions, Corp]
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package ortus.boxlang.modules.compat.bifs.cache;

import java.util.Optional;
import java.util.Set;

import ortus.boxlang.runtime.bifs.BIF;
import ortus.boxlang.runtime.bifs.BoxBIF;
import ortus.boxlang.runtime.cache.providers.ICacheProvider;
import ortus.boxlang.runtime.cache.util.CacheExistsValidator;
import ortus.boxlang.runtime.context.IBoxContext;
import ortus.boxlang.runtime.dynamic.Attempt;
import ortus.boxlang.runtime.scopes.ArgumentsScope;
import ortus.boxlang.runtime.scopes.Key;
import ortus.boxlang.runtime.types.Argument;
import ortus.boxlang.runtime.validation.Validator;

@BoxBIF
public class CacheGetAsAttempt extends BIF {

	private static final Validator cacheExistsValidator = new CacheExistsValidator();

	/**
	 * Constructor
	 */
	public CacheGetAsAttempt() {
		super();
		declaredArguments = new Argument[] {
		    new Argument( true, Argument.STRING, Key.id ),
		    new Argument( false, Argument.STRING, Key.cacheName, Key._DEFAULT, Set.of( cacheExistsValidator ) )
		};
	}

	/**
	 * Get an item from the cache and return it as a Java {@link Optional}.
	 * By default, the {@code cacheName} is set to {@code default}.
	 *
	 * @param context   The context in which the BIF is being invoked.
	 * @param arguments Argument scope for the BIF.
	 *
	 * @argument.id The cache id to retrieve
	 *
	 * @argument.cacheName The cache name to retrieve the id from, defaults to {@code default}
	 *
	 * @return The object from the cache, or an empty {@link Optional} if the object does not exist.
	 */
	public Attempt<Object> _invoke( IBoxContext context, ArgumentsScope arguments ) {
		// Get the requested cache
		ICacheProvider cache = cacheService.getCache( arguments.getAsKey( Key.cacheName ) );
		// Get it
		return cache.get( arguments.getAsString( Key.id ) );

	}
}

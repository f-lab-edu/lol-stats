/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.github.lolstats.endpoint

/**
 * @since 2021-08-10
 */
data class OkResponseEnvelope<T>(override val body: T?) : ResponseEnvelope<T>(Type.OK)

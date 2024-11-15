package org.example.pointofsalesystem.data.datasource

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.functions.Functions
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.Storage


object SupabaseClientProvider {
    private const val SUPABASE_URL = "https://fznfshlzbhfjkcqnkzuj.supabase.co"
    private const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZ6bmZzaGx6YmhmamtjcW5renVqIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzA5NTM1OTQsImV4cCI6MjA0NjUyOTU5NH0.004PdTyDEbKsou1pCkFbVDcN1yWmvq6iyDTDKOHXGg4"

    val client: SupabaseClient by lazy {
        createSupabaseClient(
            supabaseUrl = SUPABASE_URL,
            supabaseKey = SUPABASE_KEY
        ){
            install(Auth)
            install(Postgrest)
            install(Storage)
            install(Realtime)
            install(Functions)
        }
    }
}
package com.sillyapps.alarm_scheduler

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.junit.Test
import java.lang.Error


class MoshiTest {

  @Test
  fun testMoshiListAdapter() {
    val moshi = Moshi.Builder().build()
    val adapter = moshi.adapter<List<Long>>(Types.newParameterizedType(List::class.java, Long::class.javaObjectType))

    val input = "[0,1,2,3]"
    val output = adapter.fromJson(input) ?: throw Error("Cannot convert this string: $input to list of ints")
    val answer = listOf(0L, 1L, 2L, 3L)

    assert(output == answer)
  }
}
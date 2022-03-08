package com.sillyapps.core

fun <T> List<T>.findIndexOfFirst(satisfyCondition: (T) -> Boolean): Int? {
  for (i in 0 until size)
    if (satisfyCondition(get(i))) return i

  return null
}

object Math {
  fun sign(value: Int): Int {
    return if (value < 0) -1 else 1
  }

  fun sign(value: Long): Int {
    return if (value < 0) -1 else 1
  }
}


package com.sillyapps.core

fun <T> List<T>.findIndexOfFirst(satisfyCondition: (T) -> Boolean): Int? {
  for (i in 0 until size)
    if (satisfyCondition(get(i))) return i

  return null
}
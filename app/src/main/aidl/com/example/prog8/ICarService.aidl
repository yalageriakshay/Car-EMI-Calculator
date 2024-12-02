// ICarService.aidl
package com.example.prog8;

// Declare any non-default types here with import statements

interface ICarService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    float carcal(in float pa, in float dp, in float ir, in int lt);
}
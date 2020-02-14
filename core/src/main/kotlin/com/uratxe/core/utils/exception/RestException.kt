package com.baturamobile.utils.data.exception.exception

/**
 * Created by vssnake on 23/01/2018.
 */
public  open class  RestException<ErrorModel>(var codeError: Int, var errorModel : ErrorModel)
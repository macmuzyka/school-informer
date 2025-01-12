package com.schoolinformer.model.dto

import com.schoolinformer.model.enums.Validity

data class ApplicationValidityDTO(val validity: Validity, val latestVersion: String, val message: String)

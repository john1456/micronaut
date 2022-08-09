package cz.test.api.controller

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest


@MicronautTest
class ClientControllerWhichDoesNotWorkTest : StringSpec({
    "a test" {
        "aaa" shouldBe "bbb"
    }

})
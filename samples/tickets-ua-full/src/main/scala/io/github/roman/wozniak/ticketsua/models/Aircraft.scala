package io.github.roman.wozniak.ticketsua.models

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/22/15, 10:43
 */
case class Aircraft(
                     id: Long,
                     producer: String,
                     model: String,
                     registrationNumber: String,
                     numberOfSeats: Short,
                     flightCompanyId: Long)

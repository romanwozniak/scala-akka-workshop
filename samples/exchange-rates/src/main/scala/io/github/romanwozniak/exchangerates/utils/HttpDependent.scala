package io.github.romanwozniak.exchangerates.utils

import dispatch.Http

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/23/15, 17:15
 */
trait HttpDependent {

  def closeHttp() = Http.client.close()

}

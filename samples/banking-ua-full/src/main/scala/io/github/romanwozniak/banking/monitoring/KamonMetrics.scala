package io.github.romanwozniak.banking.monitoring

import kamon.Kamon

/**
 * @author Roman Wozniak <romeo.wozniak@gmail.com>
 * @version 5/10/15, 23:58
 */
trait KamonMetrics {

  Kamon.start()

  sys.addShutdownHook{
    Kamon.shutdown()
  }

}

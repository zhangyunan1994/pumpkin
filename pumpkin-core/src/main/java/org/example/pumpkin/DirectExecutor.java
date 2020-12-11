package org.example.pumpkin;

import java.util.concurrent.Executor;

public enum DirectExecutor implements Executor {

  INSTANCE;

  @Override
  public void execute(Runnable command) {
    command.run();
  }

  @Override
  public String toString() {
    return "directExecutor 他将和调用者公用一个线程";
  }
}

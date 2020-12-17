/*
 * Copyright (C) 2013 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.example.pumpkin.common.base;


/**
 * The type Verify exception.
 */
public class VerifyException extends RuntimeException {

  /**
   * Instantiates a new Verify exception.
   */
  public VerifyException() {}

  /**
   * Instantiates a new Verify exception.
   *
   * @param message the message
   */
  public VerifyException( String message) {
    super(message);
  }

  /**
   * Instantiates a new Verify exception.
   *
   * @param cause the cause
   */
  public VerifyException( Throwable cause) {
    super(cause);
  }

  /**
   * Instantiates a new Verify exception.
   *
   * @param message the message
   * @param cause   the cause
   */
  public VerifyException( String message,  Throwable cause) {
    super(message, cause);
  }
}

/**
 * Copyright (c) Anton Johansson <antoon.johansson@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.antonjohansson.lprs.controller.spam;

/**
 * Controls how many requests can be executed for a specific user name.
 */
public interface ISpamController
{
    /**
     * Checks a given user name.
     *
     * @param user The user name of the user to check.
     * @return Returns {@code true} if this user passed the check; otherwise, {@code false}.
     */
    boolean check(String user);
}

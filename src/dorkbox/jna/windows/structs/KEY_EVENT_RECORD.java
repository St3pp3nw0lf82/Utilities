/*
 * Copyright 2016 dorkbox, llc
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
package dorkbox.jna.windows.structs;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

/**
 * https://msdn.microsoft.com/en-us/library/ms684166(v=VS.85).aspx
 */
public
class KEY_EVENT_RECORD extends Structure {
    public boolean keyDown;
    public short repeatCount;
    public short virtualKeyCode;
    public short virtualScanCode;
    public CharUnion uChar;
    public int controlKeyState;

    @Override
    protected
    List<String> getFieldOrder() {
        return Arrays.asList("keyDown", "repeatCount", "virtualKeyCode", "virtualScanCode", "uChar", "controlKeyState");
    }
}

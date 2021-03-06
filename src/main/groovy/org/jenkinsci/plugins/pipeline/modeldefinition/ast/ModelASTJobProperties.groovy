/*
 * The MIT License
 *
 * Copyright (c) 2016, CloudBees, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.jenkinsci.plugins.pipeline.modeldefinition.ast

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import net.sf.json.JSONArray
import net.sf.json.JSONObject
import org.jenkinsci.plugins.pipeline.modeldefinition.validator.ModelValidator

/**
 * A container for one or more {@link ModelASTJobProperty}s
 *
 * @author Andrew Bayer
 */
@ToString(includeSuper = true, includeSuperProperties = true)
@EqualsAndHashCode(callSuper = true)
@SuppressFBWarnings(value="SE_NO_SERIALVERSIONID")
public final class ModelASTJobProperties extends ModelASTElement {
    List<ModelASTJobProperty> properties = []

    public ModelASTJobProperties(Object sourceLocation) {
        super(sourceLocation)
    }

    @Override
    public JSONObject toJSON() {
        JSONArray a = new JSONArray()
        properties.each { s ->
            a.add(s.toJSON())
        }

        return new JSONObject()
            .accumulate("properties",a)
    }

    @Override
    public void validate(ModelValidator validator) {
        validator.validateElement(this)
        properties.each { s ->
            s?.validate(validator)
        }
    }

    @Override
    public String toGroovy() {
        return "jobProperties {\n${properties.collect { it.toGroovy() }.join("\n") }\n}\n"
    }

    @Override
    public void removeSourceLocation() {
        super.removeSourceLocation()
        properties.each { it.removeSourceLocation() }
    }
}

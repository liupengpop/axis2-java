/*
 * Copyright 2004,2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.axis2.cluster.context;

import org.apache.axis2.cluster.ClusteringFault;
import org.apache.axis2.context.AbstractContext;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.description.ParameterInclude;

import java.util.List;

public interface ContextManager extends ParameterInclude {
    public void addContext(AbstractContext context) throws ClusteringFault;

    public void removeContext(AbstractContext context) throws ClusteringFault;

    public void updateContext(AbstractContext context) throws ClusteringFault;

    public boolean isContextClusterable(AbstractContext context) throws ClusteringFault;

    public void addContextManagerListener(ContextManagerListener listener);

    public void setConfigurationContext(ConfigurationContext configurationContext);

    /**
     * All properties in the context with type <code>contextType</code> which have
     * names that match the specified pattern will be excluded from replication.
     *
     * Generally, we can use the context class name as the context type.
     *
     * @param contextType
     * @param patterns    The patterns
     */
    public void setReplicationExcludePatterns(String contextType, List patterns);
}

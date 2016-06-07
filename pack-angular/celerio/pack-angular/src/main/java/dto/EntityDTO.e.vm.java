## Copyright 2015 JAXIO http://www.jaxio.com
##
## Licensed under the Apache License, Version 2.0 (the "License");
## you may not use this file except in compliance with the License.
## You may obtain a copy of the License at
##
##    http://www.apache.org/licenses/LICENSE-2.0
##
## Unless required by applicable law or agreed to in writing, software
## distributed under the License is distributed on an "AS IS" BASIS,
## WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
## See the License for the specific language governing permissions and
## limitations under the License.
##
$output.java($entity.dto)##

/**
 * Simple DTO for ${entity.model.type}.
 */
public class $output.currentClass {
#if ($entity.primaryKey.isComposite())

    // Composite primary key
    public $entity.primaryKey.type $entity.primaryKey.var;
#end
#foreach ($attribute in $entity.nonCpkAttributes.list)
#if(!$attribute.isInFk())
$output.require($attribute)##
    public $attribute.type $attribute.var;
#end
#end

#foreach ($relation in $entity.xToOne.list)
    public $relation.toEntity.dto.type $relation.to.var;
#end

    $output.dynamicAnnotation("com.fasterxml.jackson.annotation.JsonIgnore")##
    public boolean isIdSet() {
#if ($entity.primaryKey.isComposite())
        return $entity.primaryKey.var != null && ${entity.primaryKey.var}.areFieldsSet();
#else
        return $entity.primaryKey.attribute.var != null#if ($entity.primaryKey.attribute.isString() && !$entity.primaryKey.attribute.isEnum()) && !${entity.primaryKey.attribute.var}.isEmpty()#end;
#end
    }
}
package org.pos.coffee.web.json;

/**
 * Created by Laurie on 11/13/2015.
 */
public interface Scope {
    static class Base{}
    static class Search extends Base {}
    static class Form extends Search{}
    static class Full extends Form {}

}

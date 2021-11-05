package tb.personal.FreshmanPlugin;

import java.lang.reflect.Field;


public class InformationResource {
    public String pluginName;
    public InfoText infoText;
    public InfoMessage infoMessage;

    
    static public class InfoText{
        public InfoName infoName;
        public InfoDescription infoDescription;

        
        static public class InfoName{
            public String wizardWandRecipe;
        }

        
        static public class InfoDescription{
            public String wizardWandRecipe;
        }
    }

    
    static public class InfoMessage{
        public String testMessage;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        result.append( this.getClass().getName() );
        result.append( " Object {" );
        result.append(newLine);

        //determine fields declared in this class only (no fields of superclass)
        Field[] fields = this.getClass().getDeclaredFields();

        //print field names paired with their values
        for ( Field field : fields  ) {
            result.append("  ");
            try {
                result.append( field.getName() );
                result.append(": ");
                //requires access to public field:
                result.append( field.get(this) );
            } catch ( IllegalAccessException ex ) {
                System.out.println(ex);
            }
            result.append(newLine);
        }
        result.append("}");

        return result.toString();
    }


}


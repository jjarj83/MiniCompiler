public class Program {
	public static void main(String[] args) throws Exception
    {
        //  java.io.Reader r = new java.io.StringReader
        //  ("main()\n"
        //  +"{\n"
        //  +"    x = 1;\n"
        //  +"    x = _x + 1.23;\n"
        //  +"    print x;\n"
        //  +"}\n"
        //  );
        //
        //  args = new String[] { "proj1-minic-Tokenizer\\src\\test1.minc" };

        if(args.length <= 0)
            return;
        java.io.Reader r = new java.io.FileReader(args[0]);

        Compiler compiler = new Compiler(r);
        compiler.Compile();
	}
}

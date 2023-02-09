class Operator inherits Test:
    private field result int;
    public function Operator() returns int:
        return 1;
    end
    public function subtract(a:int , b:bool) returns int:
        self.result = a - b;
        return self.result;
    end
    public function arrCollector(arr:int[]) returns int:
        var counter = 0;
        while(counter < arr.length)
            self.result = self.result + arr[counter];
        return self.result;
    end
    public function comparator(a:int , b: int) returns string:
        while(a<b)
        begin
            var alaki = 3;
            if(a<0)
                print("a is negative");
            return "b is bigger than a";
        end
    end
end
entry class MainClass:
    function main() returns int:
        var a = 5;
        var b = 6;
        arr = new int[4];
        var sum=0;
        var sub=0;
        var bigger="";
        operator = new Operator();
        sub = operator.subtract();
        sum = operator.arrCollector(arr);
        bigger = operator.comparator(a,b);
        return 1;
    end
end




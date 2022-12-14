class Operator inherits Test:
    private field result int;
    public field input float;
    public function Operator() returns int:
        return 1;
    end
    public function subtractor(a:int , b:bool) returns int:
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
class Cooperator:
    private field res int;
    public field inp float;
    public function Cooperator() returns int:
        return 1;
    end
    public function sub(a:int , b:int) returns int:
        self.result = a - b;
        return self.result;
    end
    public function arrColl(arr:int[]) returns int:
        var counter = 0;
        while(counter < arr.length)
            self.result = self.result + arr[counter];
        return self.result;
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
        sub = operator.subtractor();
        sum = operator.arrCollector(arr);
        bigger = operator.comparator(a,b);
        return 1;
    end
end

class BinarySearch inherits Search :
    private field data int[];
    private field data2 , data3 , data4  int;
    private function doSearch( value : float , _begin : int , _end : int , _ensd : int ,_enzd : int ) returns int:
        var middle = ( _begin + _end ) / 2;
        var foundVal = doSearch( value , _begin , middle );
        if( !foundVal )
            foundVal = doSearch( value , middle + 1 , _end );
        return foundVal + new BinarySearch().data.length;
    end

    public function find( value : int ) returns int:
        var value1 = _doSearch( value , 0 , _data.length - 1 ).toString();
        return doSearch( value , 0 , data.length - 1 ).toString();
    end
end


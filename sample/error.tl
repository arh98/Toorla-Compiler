class Operator inherits Test:
    private field result int;
    public field result float;
    public function subfunc(a:int , b:bool) returns int:
        self.result = a - b;
        return self.result;
    end
    public function subfunc(a:int , b:bool) returns int:
            self.result = a - b;
            return self.result;
        end
end
class Operator:
    public function Operator() returns int:
        return 1;
    end
end
entry class MainClass:
    function main() returns int:
        var a = 5;
        var a = 6;
        return 1;
    end
end

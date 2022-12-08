entry class Sample1:
    function main() returns int :
        var array_size = 10;
        var array = new int[ array_size ];
        var i = 0 , i2 = 5;
        if( i ) begin
            continue;
            break;
        end
        while( i < array_size ) begin
            if( i == array_size / 2 ) begin
                array[ i ] = 1;
                continue;
            end
            array[ i ] = i + 1;
            i++;
        end
        i = 0;
        while( i < array_size ) begin
            var val = new BinarySearch().doSearch( 1 , 0 , array.length - 1 );
            val = new BinarySearch().doSearch( 0 , array.length - 1 );
        end
        return false;
    end
   private function random ( value : float  ) returns int:
           var avrege = ( _begin + _end ) / 2;
           return foundVal + new BinarySearch().data.length;
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

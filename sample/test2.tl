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
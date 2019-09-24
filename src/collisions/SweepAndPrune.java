package collisions;

import java.util.ArrayList;
import java.util.List;

public class SweepAndPrune {

    private List<AxisAlignedBoundingBox> boxes = new ArrayList<>();
    private List<EndPoint> endPointsX = new ArrayList<>();
    private List<EndPoint> endPointsY = new ArrayList<>();
    private List<EndPoint> endPointsZ = new ArrayList<>();

    private List<AxisAlignedBoundingBox> newBoxes = new ArrayList<>();
    private List<EndPoint> newEndPointsX = new ArrayList<>();
    private List<EndPoint> newEndPointsY = new ArrayList<>();
    private List<EndPoint> newEndPointsZ = new ArrayList<>();

    public void updateBox(AxisAlignedBoundingBox box, float minX, float maxX, float minY, float maxY, float minZ, float maxZ){

        box.getMinY().value = minY;
        box.getMaxY().value = maxY;
        box.getMinZ().value = minZ;
        box.getMaxZ().value = maxZ;

        // sorting the Y and Z endpoints
        sortAxis(endPointsY);
        sortAxis(endPointsZ);


        // checking X axis for collisions
        if (minX > box.getMinX().value){
            // moving right
            box.getMaxX().value = maxX;
            int index2 = endPointsX.indexOf(box.getMaxX());
            int newIndex2;
            for(newIndex2 = index2 + 1; newIndex2 < endPointsX.size(); newIndex2++){
                if(box.getMaxX().value > endPointsX.get(newIndex2).value){
                    if (endPointsX.get(newIndex2).isMinimum() && endPointsX.get(newIndex2).getOwner() != box){
                        AxisAlignedBoundingBox comparativeBox = endPointsX.get(newIndex2).getOwner();
                        if (checkBoxOverlapYZ(box, comparativeBox)){
                            // update pair manager with  overlap
                            System.out.println("start overlap");
                        }
                    }
                }else{
                    break;
                }
            }
            endPointsX.remove(box.getMaxX());
            endPointsX.add(newIndex2-1, box.getMaxX());


            box.getMinX().value = minX;
            int index = endPointsX.indexOf(box.getMinX());
            int newIndex;
            for (newIndex = index + 1; newIndex < endPointsX.size(); newIndex++){
                if(box.getMinX().value > endPointsX.get(newIndex).value){
                    if (!endPointsX.get(newIndex).isMinimum() && endPointsX.get(newIndex).getOwner() != box){
                        System.out.println("stop overlap");
                    }
                }else{
                    break;
                }
            }
            endPointsX.remove(box.getMinX());
            endPointsX.add(newIndex-1, box.getMinX());



        }else if(minX < box.getMinX().value){
            // moving left
            box.getMinX().value = minX;
            int index = endPointsX.indexOf(box.getMinX());
            int newIndex;
            for (newIndex = index - 1; newIndex >= 0; newIndex--){
                if(box.getMinX().value < endPointsX.get(newIndex).value){
                    if (!endPointsX.get(newIndex).isMinimum() && endPointsX.get(newIndex).getOwner() != box){
                        AxisAlignedBoundingBox comparativeBox = endPointsX.get(newIndex).getOwner();
                        if (checkBoxOverlapYZ(box, comparativeBox)){
                            // update pair manager with overlap
                            System.out.println("start overlap");
                        }
                    }
                }else{
                    break;
                }
            }

            endPointsX.remove(box.getMinX());
            endPointsX.add(newIndex+1, box.getMinX());

            box.getMaxX().value = maxX;
            int index2 = endPointsX.indexOf(box.getMaxX());
            int newIndex2;
            for(newIndex2 = index2 - 1; newIndex2 >=0; newIndex2--){
                if(box.getMaxX().value < endPointsX.get(newIndex2).value){
                    if (endPointsX.get(newIndex2).isMinimum() && endPointsX.get(newIndex2).getOwner() != box){
                        System.out.println("stop overlap");
                    }
                }else{
                    break;
                }
            }
            endPointsX.remove(box.getMaxX());
            endPointsX.add(newIndex2+1, box.getMaxX());
        }

    }

    public void addBox(AxisAlignedBoundingBox box){
        newBoxes.add(box);
        newEndPointsX.add(box.getMinX());
        newEndPointsX.add(box.getMaxX());
        newEndPointsY.add(box.getMinY());
        newEndPointsY.add(box.getMaxY());
        newEndPointsZ.add(box.getMinZ());
        newEndPointsZ.add(box.getMaxZ());

    }

    public void organiseNewBoxes(){
        // Called every frame
        sortAxis(newEndPointsX);
        sortAxis(newEndPointsY);
        sortAxis(newEndPointsZ);
        
        for(int i=endPointsX.size()-1; i > 0; i--){
        
        }
    }


    private void sortAxis(List<EndPoint> axis){
        for(int i = 1; i < axis.size(); i++){
            EndPoint item = axis.get(i);
            if(item.value < axis.get(i - 1).value){
                int attemptPos = i - 1;
                while(attemptPos != 0 && item.value < axis.get(i - 1).value){
                    attemptPos--;
                }
                axis.remove(i);
                axis.add(attemptPos, item);
            }
        }
    }

    private boolean checkBoxOverlapYZ(AxisAlignedBoundingBox a, AxisAlignedBoundingBox b){
        // checking if overlap on y and z axis
         return  (a.getMinY().value <= b.getMaxY().value && a.getMaxY().value >= b.getMinY().value) &&
                 (a.getMinZ().value <= b.getMaxZ().value && a.getMaxZ().value >= b.getMinZ().value);



    }

}

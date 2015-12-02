#check if 7zip is present
If (-not (Test-Path "$env:ProgramFiles\7-Zip\7z.exe")) {
    Throw "$env:ProgramFiles\7-Zip\7z.exe needed"
} 
Set-Alias sz "$env:ProgramFiles\7-Zip\7z.exe" 

#set user path
$userPath = "C:\Users\morte_o3ehocu";

#set source and destination folders
$sourceFolder = "$userPath\Dropbox (Itema)\Statoil";
$destinationFolder = "$userPath\Desktop\Modelshare\Repository";
$modelAccessFile = "$userPath\Desktop\rights.access";
$modelDestinationSubFolder = "output\x3d";
$modelPreviewFile = "x3d.project.jpg";

#define root folder structure
$rootLevelFolders = "DPN - Operations North",
                    "DPN - Operations West",
                    "DPN - Operations South",
                    "DPI - International",
                    "Other Units";

#define output folder structure
$outputLevelFolders = "Modelling",
                      "Visualisation",
                      "Analysis";

#define source folders to be zipped (add dwg, step, igs and obj when needed)
$sourceFoldersToZip = "fbx",
                      "max2014",
                      "input";

#create folder lookup hashtable
$rootLevelFolderLookup = @{};
$rootLevelFolderLookup.Add("norne", $rootLevelFolders[0]);
$rootLevelFolderLookup.Add("aasgard_a", $rootLevelFolders[0]);
$rootLevelFolderLookup.Add("aasgard_b", $rootLevelFolders[0]);
$rootLevelFolderLookup.Add("aasgard_c", $rootLevelFolders[0]);
$rootLevelFolderLookup.Add("heidrun", $rootLevelFolders[0]);
$rootLevelFolderLookup.Add("kristin", $rootLevelFolders[0]);
$rootLevelFolderLookup.Add("tyrihans_d", $rootLevelFolders[0]);
$rootLevelFolderLookup.Add("tyrihans_w", $rootLevelFolders[0]);
$rootLevelFolderLookup.Add("njord_a", $rootLevelFolders[0]);
$rootLevelFolderLookup.Add("njord_bravo", $rootLevelFolders[0]);
$rootLevelFolderLookup.Add("troll_b", $rootLevelFolders[1]);
$rootLevelFolderLookup.Add("troll_c", $rootLevelFolders[1]);
$rootLevelFolderLookup.Add("navion_saga", $rootLevelFolders[1]);
$rootLevelFolderLookup.Add("visund", $rootLevelFolders[1]);
$rootLevelFolderLookup.Add("veslefrikk_b", $rootLevelFolders[1]);
$rootLevelFolderLookup.Add("grane", $rootLevelFolders[1]);
$rootLevelFolderLookup.Add("kvitebjorn", $rootLevelFolders[1]);
$rootLevelFolderLookup.Add("valemon", $rootLevelFolders[1]);
$rootLevelFolderLookup.Add("snorre_a", $rootLevelFolders[2]);
$rootLevelFolderLookup.Add("snorre_b", $rootLevelFolders[2]);
$rootLevelFolderLookup.Add("mariner", $rootLevelFolders[3]);
$rootLevelFolderLookup.Add("petrojarl", $rootLevelFolders[4]);
$rootLevelFolderLookup.Add("saipem_7000", $rootLevelFolders[4]);
$rootLevelFolderLookup.Add("elisabeth_knutsen", $rootLevelFolders[4]);
$rootLevelFolderLookup.Add("hanne_knutsen", $rootLevelFolders[4]);
$rootLevelFolderLookup.Add("jorunn_knutsen", $rootLevelFolders[4]);
$rootLevelFolderLookup.Add("ff1000s", $rootLevelFolders[4]);
$rootLevelFolderLookup.Add("ge40", $rootLevelFolders[4]);

$folderNameChange = @{};
$folderNameChange.Add("jorunn_knutsen", "aasgard c");
$folderNameChange.Add("navion_saga", "volve");
$folderNameChange.Add("mariner", "mariner jacket");
$folderNameChange.Add("petrojarl", "petrojarl 1");

$modelNameChange = @{};
$modelNameChange.Add("tyrihans_d", "tyrihans_d");
$modelNameChange.Add("tyrihans_w", "tyrihans_w");

#create root folder structure in destination
ForEach($folder in $rootLevelFolders) {
    New-Item $destinationFolder\$folder -Type Directory;
}

Write-Host;

#get source folders
$sourceFolders = Get-ChildItem $sourceFolder -Directory;

ForEach($folder in $sourceFolders) {
    #get installation name
    $installationName = $folder.Name;
    Write-Host ("{0}: {1}" -f "Extracted installation name", $installationName);

    #get 5th part of installation name and use as model name
    $fileNameParts = $installationName.Split(".");
    $modelName = $fileNameParts[4];
    $modelType = $fileNameParts[5];
    Write-Host ("{0}: {1}" -f "Extracted model name", $modelName);
    Write-Host ("{0}: {1}" -f "Extracted model type", $modelType);

    If($modelType -eq "asbuilt") {
        Write-Host ("{0}: {1}" -f "Processing", $folder.FullName);

        #look up output root folder
        $outputRootFolder = $rootLevelFolderLookup[$modelName];
        Write-Host ("{0}: {1}" -f "Output root folder", $outputRootFolder);

        #check for folder name change
        $modelNameChange = $folderNameChange[$modelName];
        If($modelNameChange -ne $null) {
            Write-Host ("{0} '{1}': '{2}'" -f "Found new name for", $modelName, $modelNameChange);
            $modelName = $modelNameChange;
        }

        #create model folder, substitute underscore with space
        $modelName = $modelName.Replace("_", " ");
        Write-Host ("{0}: {1}" -f "Creating model folder", $modelName);
        $output = New-Item $destinationFolder\$outputRootFolder\$modelName -Type Directory;
    
        #create sub folders
        Write-Host ("{0}: {1}" -f "Creating model sub folders folders in", $output);
        ForEach($folder in $outputLevelFolders) {
            $newFolder = New-Item $output\$folder -Type Directory;
            Write-Host $newFolder;
        }

        #Modelling
        Write-Host "Copying to Modelling";
        $sourceRoot = "$sourceFolder\$installationName\$modelDestinationSubFolder\$installationName";
        
        #copy .access file
        Write-Host "Copying .access file to Modelling folder";
        Copy-Item $modelAccessFile -Destination "$output\Modelling\.access";
        #copy preview file
        $sourcePreview = "$sourceRoot\$modelPreviewFile";
        $destinationPreview = "$destinationFolder\$outputRootFolder\$modelName.jpg"
        Write-Host ("Copying preview file to output root folder ({0} to {1})" -f $sourcePreview, $destinationPreview);
        Copy-Item $sourcePreview -Destination $destinationPreview;
        
        #zip and copy models
        ForEach($folder in $sourceFoldersToZip) {
            $zipArchiveName = $folder;
            $source = "$sourceRoot\$folder";
            $destination = "$output\Modelling\$zipArchiveName";

            Write-Host ("Zipping folder {0} to archive {1}" -f $source, $destination);
            DoZip $source $destination;
        }

        #Visualisation
        Write-Host "Copying to Visualisation";
        #copy .hsf
        Copy-Item $sourceFolder\$installationName\$modelDestinationSubFolder\$installationName\hoops\*.hsf -Destination $output\Visualisation
        #copy .vtf
        Copy-Item $sourceFolder\$installationName\$modelDestinationSubFolder\$installationName\vtf\*.vtf -Destination $output\Visualisation
        #copy .wrl
        Copy-Item $sourceFolder\$installationName\$modelDestinationSubFolder\$installationName\vrml\*.wrl -Destination $output\Visualisation

        #Analysis
        Write-Host "Copying to Analysis";
        #copy .stl
        Copy-Item $sourceFolder\$installationName\$modelDestinationSubFolder\$installationName\stl\*.stl -Destination $output\Analysis
    
        Write-Host;
    }
    Else {
        Write-Host "Skipping non asbuilt models";
        Write-Host;
    }

    Function DoZip($sourceFolder, $destinationFile) {
        Write-Host ("sz a -tzip -r {0} {1}" -f $destinationFile, $sourceFolder);
        sz a -tzip -r $destinationFile $sourceFolder;
    }
}
